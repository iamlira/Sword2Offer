package com.elasticcloudservice.predict;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Predict {

	public static String type = "CPU";                 								//优化纬度
	public static int[][] server = new int[200][20];  								//分配结果
	public static int[][] server1 = new int[200][20];  								//填补后的结果
	public static int count = 0;        											//填补的数量
	public static int server_count = 0; 											//填补后的服务器个数
	public static Map<String, Integer> freshmap = new LinkedHashMap<>(); 			//填补后的预测结果
	public static Map<String, Integer> flavorsinlastserver = new LinkedHashMap<>(); //最后一个服务器内的虚拟机 key： name value ： number


	public static String[] predictVm(String[] ecsContent, String[] inputContent) {

		/** =========do your work here========== **/
		String[] results = new String[ecsContent.length];							//返回结果，即符合要求的输出格式
		List<String> history = new ArrayList<String>();								//存储训练数据的各个记录，形式id+" "+flavor+" "+请求时间
		int serverNum = Integer.valueOf(inputContent[0]); 							//服务器个数
		int flavorNum = Integer.valueOf(inputContent[serverNum+2]); 				//预测虚拟机种类
		Map<String, SA.Server> serverMap = new LinkedHashMap<>(); 					//服务器名字加规格
		String[] serverName = new String[serverNum];								//服务器名字
		String[] target_f = new String[flavorNum];									//用于存储input文件的各规格虚拟机名字
		float[] target_f_num = new float[flavorNum];								//用于存储预测出的各规格虚拟机的数量
		String begin_date = "2900-1-1 00:00:00",end_date = "1900-1-1 00:00:00";		//记录训练数据中的最近请求日期和最远请求日期
		long day=0,pre_day=0,pre_gap_day=0;														//day为训练数据请求跨度天数，pre_day为预测区间天数
		int[] f_cpu=new int[flavorNum],f_mem=new int[flavorNum];					//获取各规格虚拟机参数，第i各索引就代表第i个虚拟机参数
		int[] server_cpu=new int[serverNum],server_mem=new int[serverNum];

		//------使用自己定义的类-------//
		Time_Oper time_oper=new Time_Oper();
		ESmooth eSmooth = new ESmooth(0.75);
		DataDry dataDry = new DataDry();
		DataSplit dataSplit =new DataSplit();

		//------服务器参数-------//
		SA saa = new SA(); SA.Server server_tmp = null; String[] str_temp = null;
		for (int i = 1; i < serverNum+1;i++){
			str_temp = inputContent[i].split(" ");
			server_cpu[i-1]=Integer.valueOf(str_temp[1]);
			server_mem[i-1]=Integer.valueOf(str_temp[2]);
			server_tmp = saa.new Server(Integer.valueOf(str_temp[2])*1024,Integer.valueOf(str_temp[1]));
			serverMap.put(str_temp[0],server_tmp);
			serverName[i-1] = str_temp[0];
			server_tmp = null; str_temp = null;
			System.gc();
		}

		//------遍历训练数据，存储至history-------//
		int max_flavor=0;
		for (int i = 0; i < ecsContent.length; i++) {
			if (ecsContent[i].contains(" ")
					&& ecsContent[i].split("\t").length == 3) {
				String[] array = ecsContent[i].split("\t");
				String uuid = array[0];
				String flavorName = array[1];
				String createTime = array[2];
				if(begin_date.compareTo(createTime)>0){
					begin_date=createTime;
				}
				if(end_date.compareTo(createTime)<0){
					end_date=createTime;
				}
				history.add(uuid + " " + flavorName + " " + createTime);

				int str_max_flavor=Integer.valueOf(flavorName.substring(6));		//找到最大型号数字
				if (str_max_flavor>max_flavor)
					max_flavor=str_max_flavor;
			}
		}

		//------从input中获取各指定预测虚拟机的参数-------//
		int count=0;
		for (int i = 1; i < inputContent.length; i++) {
			if(inputContent[i].contains("flavor")) {
				String[] tmp=inputContent[i].split(" ");
				target_f[count]=tmp[0];
				f_cpu[count]=Integer.valueOf(tmp[1]);
				f_mem[count]=Integer.valueOf(tmp[2]);
				count++;
			}
		}

		//------时间处理-------//
		int id = serverNum + flavorNum + 4;
		String pre_begin = inputContent[id].split(" ")[0];
		String pre_end = inputContent[id+1].split(" ")[0];
		pre_day = time_oper.calcu_day_diff(pre_begin,pre_end);						//计算预测天数
		day = time_oper.calcu_day_diff(begin_date,end_date);						//得到训练天数
		pre_gap_day=time_oper.calcu_day_diff(end_date,pre_begin);					//得到预测间隔时间


		//-------获取目标规格虚拟机每日访问数量------//
		// --------获取所有规格每日访问量----------//
		int[][] all_f_num_ed=new int[max_flavor][(int)day+1];
		int[][] f_num_ed =new int[target_f_num.length][(int)day+1];					//该二维数组为每种型号每年的访问量,f_num_everyday
		for (int i = 0; i < history.size(); i++) {									//遍历请求历史记录，如果出现需要预测的虚拟机，则需要预测的虚拟机的对应num++; 这里统计历史记录中需要预测虚拟机出现次数总和
			String[] this_day = history.get(i).split(" ");
			int day_index = time_oper.calcu_day_diff(begin_date,this_day[2]);
			for(int j=0;j<target_f.length;j++){
				if (this_day[1].equals(target_f[j])){
					target_f_num[j]++;
					f_num_ed[j][day_index]++;
				}
			}
			int flavor_index=Integer.valueOf(this_day[1].substring(6));
			all_f_num_ed[flavor_index-1][day_index]++;
		}

		//------获取二次指数平滑的历史数据-------//
		double[][] new_data=dataDry.AllChauvenet_double(f_num_ed);
		double[][] smooth_data=dataSplit.SplitDataBlock_double(new_data,(int)pre_day);

		//若预测时间太长，则用训练数据最后几天代替
		int[] target_f_num_qu=new int[target_f.length];
		if(smooth_data[0].length==1){
			for(int i=0;i<f_num_ed.length;i++){
				for(int index=f_num_ed[i].length-1,j=0;j<pre_day;j++,index--){
					target_f_num_qu[i]+=(int)f_num_ed[i][index];
				}
			}
		}
		else {
			//------获取每天各虚拟机历史访问数据-------//
			double[][] f_sum_week = new double[target_f_num.length][smooth_data[0].length];
			for (int i = 0; i < f_sum_week.length; i++) {
				f_sum_week[i][0] = smooth_data[i][0];
				for (int j = 1; j < f_sum_week[0].length; j++) {
					f_sum_week[i][j] = f_sum_week[i][j - 1] + smooth_data[i][j];
				}
			}

			//------使用二次指数平滑预测-------//
			double[] result_1 = eSmooth.Second_ES_With_a1_and_a2(smooth_data, 0.73, 0.335, (double) pre_gap_day / (double) pre_day + 1);
			double[] result_2 = eSmooth.Second_ES_With_a1_and_a2(f_sum_week, 0.9, 0.62, (double) pre_gap_day / (double) pre_day + 1);

			for (int i = 0; i < result_2.length; i++) {
				result_2[i] = result_2[i] - f_sum_week[i][f_sum_week[i].length - 1];
			}

			//stacking
			Stacking stack = new Stacking();
			double stepSize = stack.getBestStepSize(result_1, result_2);
			double[][] stacking_train = stack.getStackingTrainData_ES(smooth_data, f_sum_week, 0.65, 0.46, 0.56, 0.45);
			double[] stacking_label = stack.getStackingLabelData_ES(smooth_data);
			stack.init(stacking_train, stacking_label, 1000, stepSize, 1, stacking_train[0].length, stacking_train.length);
			stack.train();
			double[] result = stack.predict(result_1, result_2);
			//------预测结果处理-------//
			for (int i = 0; i < target_f_num.length; i++)
				target_f_num_qu[i] = (int) Math.ceil(result[i]);                        //使用指数平滑用这句
		}
		int target_num_all_qu = 0;//总体需求量
		for(int i=0;i<target_f_num_qu.length;i++){
			target_num_all_qu+=target_f_num_qu[i];
		}

		Allocation allocation=new Allocation();
		allocation.init(target_f_num_qu,f_cpu,f_mem,serverNum,server_cpu,server_mem);
		int[][] flavorInServers=allocation.allocate();

		//filling
		Map<String, SA.Flavor> re = new LinkedHashMap<String, SA.Flavor>(); 	//虚拟机的规格：名字 + flavor（内部类虚拟机的规格）
		SA sa = new SA();

		//------ga数据处理-------//
		for (int i = 0; i < target_f.length; i++) {
			SA.Flavor flavor = sa.new Flavor("", 0, 0);
			flavor.name = target_f[i];
			flavor.cpu = f_cpu[i];
			flavor.mem = f_mem[i];
			re.put(flavor.name, flavor);
			flavor = null;
			System.gc();
		}
		int[] num_of_type = allocation.numOfServerType;
		int g_num = num_of_type[Allocation.GENERAL];
		int l_num = num_of_type[Allocation.LARGEM];
		int h_num = num_of_type[Allocation.HIGHP];
		int[][] server_g = new int[g_num][target_f_num_qu.length];
		int[][] server_l = new int[l_num][target_f_num_qu.length];
		int[][] server_h = new int[h_num][target_f_num_qu.length];

		for (int i = 0; i < g_num;i++){
			for (int j = 0; j < target_f_num_qu.length; j++){
				server_g[i][j] = flavorInServers[i][j];
			}
		}

		for (int i = g_num, k = 0; i < l_num+g_num; i++, k++){
			for (int j = 0; j < target_f_num_qu.length; j++){
				server_l[k][j] = flavorInServers[i][j];
			}
		}

		for (int i = l_num+g_num,k = 0; i < h_num+l_num+g_num; i++,k++){
			for(int j = 0; j < target_f_num_qu.length; j++){
				server_h[k][j] = flavorInServers[i][j];
			}
		}

		FillRestServer fillRestServer = new FillRestServer();
		int len_1 = g_num+l_num+h_num; int len_2 = target_f_num_qu.length;
		int[][] final_flavorInServers = new int[len_1][len_2];
		if (g_num != 0) {
			int[][] after_fill_g = fillRestServer.Fill(server_g, 56, 128 * 1024, g_num, target_f, re);
			for (int i = 0; i < g_num; i++){
				for(int j = 0; j < len_2;j++){
					final_flavorInServers[i][j] = after_fill_g[i][j];
				}
			}
		}
		if (l_num != 0){
			int[][] after_fill_l = fillRestServer.Fill(server_l,84,256*1024,l_num,target_f,re);
			for (int i = 0, k = g_num; k < g_num + l_num; i++,k++){
				for(int j = 0; j < len_2;j++){
					final_flavorInServers[k][j] = after_fill_l[i][j];
				}
			}
		}
		if (h_num != 0){
			int[][] after_fill_h = fillRestServer.Fill(server_h,112,192*1024,h_num,target_f,re);
			for (int i = 0, k = g_num + l_num; k < len_1; i++, k++){
				for(int j = 0; j < len_2;j++){
					final_flavorInServers[k][j] = after_fill_h[i][j];
				}
			}
		}

		int tmp = 0; int imp = 0; 													// 临时变量
		int sum = 0;              													// 剔除所有虚拟机的和
		//------填补后修改预测值-------//
		for (String key : freshmap.keySet()) {
			Predict.count += freshmap.get(key);
			for (int i = 0; i < target_f.length; i++) {
				if (key.equals(target_f[i])) {
					tmp = freshmap.get(key);
					target_f_num_qu[i] = tmp + target_f_num_qu[i];
				}
			}
		}

		//------填补后修改预测值（最后一个服务器）-------//
		for (String key : flavorsinlastserver.keySet()){
			for (int i = 0; i < target_f.length; i++){
				if (key.equals(target_f[i])){
					imp = flavorsinlastserver.get(key);
					sum += imp;
					target_f_num_qu[i] = target_f_num_qu[i] - imp;
				}
			}
		}
		target_num_all_qu = target_num_all_qu + Predict.count - sum;  				//填补后的预测总数

		//---贪心算法输出结果
		int result_index = 0;
		results[result_index++] = target_num_all_qu + "\r";
		for (int j = 0; j < target_f.length; j++) {
			results[result_index++] = target_f[j] + " " + target_f_num_qu[j] + "\r";
		}
		results[result_index++] = "\r";
		for(int j=0;j<serverNum;j++){
			switch (j) {
				case Allocation.GENERAL:
					int general_count=1;
					if(allocation.numOfServerType[Allocation.GENERAL]!=0)
						results[result_index++]=serverName[j]+" "+allocation.numOfServerType[Allocation.GENERAL];
					for(int k=allocation.startIndexOfGeneral;k<allocation.startIndexOfLargeMEM;k++){
						results[result_index]="General-"+general_count+" ";
						for(int l=0;l<final_flavorInServers[k].length;l++){
							if(final_flavorInServers[k][l]!=0)
								results[result_index]+=target_f[l]+" "+final_flavorInServers[k][l]+" ";
						}
						results[result_index++]+='\r';
						general_count++;
					}
					if(allocation.numOfServerType[Allocation.GENERAL]!=0) {
						results[result_index] = "\r";
						result_index++;
					}
					break;
				case Allocation.LARGEM:
					int largem_count=1;
					if(allocation.numOfServerType[Allocation.LARGEM]!=0)
						results[result_index++]=serverName[j]+" "+allocation.numOfServerType[Allocation.LARGEM];
					for(int k=allocation.startIndexOfLargeMEM;k<allocation.startIndexOfHighPerform;k++){
						results[result_index]="Large-Memory-"+largem_count+" ";
						for(int l=0;l<final_flavorInServers[k].length;l++){
							if(final_flavorInServers[k][l]!=0)
								results[result_index]+=target_f[l]+" "+final_flavorInServers[k][l]+" ";
						}
						results[result_index++]+='\r';
						largem_count++;
					}
					if(allocation.numOfServerType[Allocation.LARGEM]!=0) {
						results[result_index] = "\r";
						result_index++;
					}
					break;
				case Allocation.HIGHP:
					int highp_count=1;
					if(allocation.numOfServerType[Allocation.HIGHP]!=0)
						results[result_index++]=serverName[j]+" "+allocation.numOfServerType[Allocation.HIGHP];
					for(int k=allocation.startIndexOfHighPerform;k<final_flavorInServers.length;k++){
						results[result_index]="High-Performance-"+highp_count+" ";
						for(int l=0;l<final_flavorInServers[k].length;l++){
							if(final_flavorInServers[k][l]!=0)
								results[result_index]+=target_f[l]+" "+final_flavorInServers[k][l]+" ";
						}
						results[result_index++]+='\r';
						highp_count++;
					}
					if(allocation.numOfServerType[Allocation.HIGHP]!=0) {
						results[result_index] = "\r";
						result_index++;
					}
					break;
			}
		}
		return results;
	}

}
