package com.elasticcloudservice.predict;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Predict {

	public static String type = "CPU";                 // 优化纬度
	public static int[][] server = new int[200][20];   // 分配结果
	public static int[][] server1 = new int[200][20];  //填补后的结果
	public static int count = 0;        //填补的数量
	public static int server_count = 0; //填补后的服务器个数
	public static Map<String, Integer> freshmap = new LinkedHashMap<>(); //填补后的预测结果
	public static Map<String, Integer> flavorsinlastserver = new LinkedHashMap<>(); //最后一个服务器内的虚拟机 key： name value ： number


	public static String[] predictVm(String[] ecsContent, String[] inputContent) {

		/** =========do your work here========== **/
		String[] results = new String[ecsContent.length];//返回结果，即符合要求的输出格式

		List<String> history = new ArrayList<String>();//存储训练数据的各个记录，形式id+" "+flavor+" "+请求时间
		String[] target_f = new String[Integer.valueOf(inputContent[2])];//用于存储input文件的各规格虚拟机名字
		float[] target_f_num = new float[Integer.valueOf(inputContent[2])];//用于存储预测出的各规格虚拟机的数量
		int target_num_all=0;//预测的虚拟机总量，输出文件有要求
		String begin_date = "2900-1-1 00:00:00",end_date = "1900-1-1 00:00:00";//记录训练数据中的最近请求日期和最远请求日期
		long day=0,pre_day=0;//day为训练数据请求跨度天数，pre_day为预测区间天数
		String[] params=inputContent[0].split(" ");//获取物理服务器的各参数
		int cpu=Integer.valueOf(params[0]),mem=Integer.valueOf(params[1])*1024,hard=Integer.valueOf(params[2]);
		int[] f_cpu=new int[Integer.valueOf(inputContent[2])],f_mem=new int[Integer.valueOf(inputContent[2])];//获取各规格虚拟机参数，第i各索引就代表第i个虚拟机参数

		//使用自己定义的类
		Time_Oper time_oper=new Time_Oper();//日期操作类
		ESmooth eSmooth = new ESmooth(0.75);
		DataDry dataDry = new DataDry();
		DataSplit dataSplit =new DataSplit();
		DataTransform dataTransform=new DataTransform();
		//分配类
		Allocation allocation=new Allocation();

		int max_flavor=0;
		for (int i = 0; i < ecsContent.length; i++) {//遍历训练数据，存储至history

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
				//找到最大型号数字
				int str_max_flavor=Integer.valueOf(flavorName.substring(6));
				if (str_max_flavor>max_flavor)
					max_flavor=str_max_flavor;
			}
		}


		int count=0,input_index=0;
		for (int i = 1; i < inputContent.length; i++) {//从input中获取各指定预测虚拟机的参数
			if(inputContent[i].contains("flavor")) {
				String[] tmp=inputContent[i].split(" ");
				target_f[count]=tmp[0];
				f_cpu[count]=Integer.valueOf(tmp[1]);
				f_mem[count]=Integer.valueOf(tmp[2]);
				allocation.inputData.flavorList[count].flavorName=target_f[count];
				allocation.inputData.flavorList[count].flavorNO=Integer.valueOf(target_f[count].substring(6));
				allocation.inputData.flavorList[count].cpuNum=f_cpu[count];
				allocation.inputData.flavorList[count].memoryNum=f_mem[count];
				count++;
				input_index=i;
				//System.out.println(target_f[count-1]);
			}
		}
		input_index+=2;
		allocation.inputData.maxCpu=cpu;
		allocation.inputData.maxMem=mem;
		allocation.inputData.flavorNum=count;
		//allocation.inputData.priorityResource=inputContent[input_index].equals("CPU")?Allocation.CPUFIRST:Allocation.MEMORYFIRST;
		type = inputContent[input_index];
		input_index+=2;

		String pre_begin_data=inputContent[input_index++].split(" ")[0];
		String pre_end_data=inputContent[input_index].split(" ")[0];
		pre_day = time_oper.calcu_day_diff(pre_begin_data,pre_end_data);//计算预测天数
		day = time_oper.calcu_day_diff(begin_date,end_date);//得到训练天数

		//-------获取目标规格虚拟机每日访问数量------//
		// --------获取所有规格每日访问量----------//
		int[][] all_f_num_ed=new int[max_flavor][(int)day+1];
		int[][] f_num_ed =new int[target_f_num.length][(int)day+1];//该二维数组为每种型号每年的访问量,f_num_everyday
		for (int i = 0; i < history.size(); i++) {//遍历请求历史记录，如果出现需要预测的虚拟机，则需要预测的虚拟机的对应num++;这里统计历史记录中需要预测虚拟机出现次数总和
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
			//results[i] = history.get(i);
		}

		//获取二次指数平滑的历史数据
		//dataDry.Quartile(f_num_ed);
		double[][] new_data=dataDry.AllChauvenet_double(f_num_ed);
		double[][] smooth_data=dataSplit.SplitDataBlock_double(new_data,(int)pre_day);
		//smooth_data = eSmooth.Single_ES_With_alpha_int(smooth_data,(float)0.64);


		//时间序列聚类
//		Dtw dtw=new Dtw();
//		double dtw_data[][]=dtw.getAllDistance(all_f_num_ed,f_num_ed);
//		for(int i=0;i<dtw_data.length;i++){
//			int dtw_count=0;
//			for (int j=0;j<dtw_data[i].length;j++){
//				if(dtw_data[i][j]<=10)
//					dtw_count++;
//			}
//			int[][] dtw_train_data=new int[dtw_count][all_f_num_ed[0].length];
//			for(int j=0;j<dtw_data[i].length;j++){
//				int tmp_count=0;
//				if(dtw_data[i][j]<=10){
//					dtw_train_data[tmp_count]=all_f_num_ed[j].clone();
//					tmp_count++;
//				}
//			}
//			int[][] dtw_train_data_block=dataSplit.SplitDataBlockWithT(dtw_train_data,(int)pre_day);
//			double[][] train_data=dataSplit.GetLinearTrainDataWithBias(dtw_train_data_block,(int)pre_day);
//			double[] label_data=dataSplit.GetLinearLabelData(dtw_train_data_block,(int)pre_day);
//			double[][] test_data=dataSplit.GetLinearTestDataWithBias(dtw_train_data_block,(int)pre_day);
//			double[] Weights = new double[train_data[0].length];
//			LinearRegressionWithSGD model = new LinearRegressionWithSGD(train_data, label_data,
//					100, 0.0001, 1, Weights, 0.00001, train_data[0].length, train_data.length);
//			model.train();
//			double[] res_pre = model.predict(test_data) ;
//			int[] result=dataTransform.Double2Int(res_pre);
//		}
		//线性回归
//		double[][] train_data=dataSplit.GetLinearTrainDataWithBias(smooth_data,(int)pre_day);
//		double[] label_data=dataSplit.GetLinearLabelData(smooth_data,(int)pre_day);
//		double[][] test_data=dataSplit.GetLinearTestDataWithBias(smooth_data,(int)pre_day);
//		double[] Weights = new double[train_data[0].length];
//		LinearRegressionWithSGD model = new LinearRegressionWithSGD(train_data, label_data,
//				100, 0.0001, 1, Weights, 0.00001, train_data[0].length, train_data.length);
//		model.train();
//		double[] res_pre = model.predict(test_data) ;
//		int[] result=dataTransform.Double2Int(res_pre);


		//获取每天各虚拟机历史访问数据
		int[][] f_sum_ed=new int[target_f_num.length][(int)day+1];
		for(int i=0;i<f_sum_ed.length;i++){
			f_sum_ed[i][0]=f_num_ed[i][0];
			for(int j=1;j<f_sum_ed[0].length;j++){
				f_sum_ed[i][j] = f_sum_ed[i][j-1]+f_num_ed[i][j];
			}
		}
		//dataDry.Quartile(f_num_ed);
		//使用二次指数平滑预测
		//double[] result=eSmooth.Second_ES_With_a1_and_a2(smooth_data,0.55,0.78);
		double[] result=eSmooth.Second_ES_With_a1_and_a2(smooth_data,0.73,0.335);

		//预测结果处理
		int[] target_f_num_qu=new int[target_f.length];//每个虚拟机的总平均需求量
		int target_num_all_qu=0;//总体需求量
		for(int i = 0; i<target_f_num.length;i++)
			target_f_num_qu[i] = (int)Math.ceil(result[i]);//使用指数平滑用这句
		//target_f_num_qu[i] =result[i];//线性回归用这句
		for(int i=0;i<target_f_num_qu.length;i++){
			target_num_all_qu+=target_f_num_qu[i];
		}

		///分配
		Map<String, Integer> map = new LinkedHashMap<String, Integer>(); // 预测出的虚拟机：名字+数量
		Map<String, SA.Flavor> re = new LinkedHashMap<String, SA.Flavor>(); //虚拟机的规格：名字 + flavor（内部类虚拟机的规格）
		SA sa = new SA();
		Vector<SA.Server> tt = new Vector<>();//存储结果
		for (int i = 0; i < target_f.length; i++) {
			SA.Flavor flavor = sa.new Flavor("", 0, 0);
			flavor.name = target_f[i];
			flavor.cpu = f_cpu[i];
			flavor.mem = f_mem[i];
			map.put(flavor.name, target_f_num_qu[i]);
			re.put(flavor.name, flavor);
			flavor = null;
			System.gc();
		}

		Vector<SA.Flavor> vec_flavors = new Vector<>();
		for (String key : map.keySet()) {
			int v = map.get(key);
			while (v-- != 0) {
				vec_flavors.add(re.get(key));
			}
		}
		int[] cpu_data = new int[vec_flavors.size()];
		int[] mem_data = new int[vec_flavors.size()];
		for (int i = 0; i < vec_flavors.size(); i++) {
			cpu_data[i] = vec_flavors.get(i).cpu;
			mem_data[i] = vec_flavors.get(i).mem;
		}
		//ga
		LinkedHashMap<Integer, Integer> map1 = new LinkedHashMap<>();
		int m = 0;
		for (int i = 0; i < target_f_num_qu.length; i++) {
			for (int j = 0; j < target_f_num_qu[i]; j++) {
				map1.put(m, i + 1);
				m++;
			}

		}
		//遗传
		GA ga = new GA(30, vec_flavors.size(), 3000, 0.85f, 0.15f, cpu, mem, cpu_data, mem_data, map1);
		ga.init();                  //初始化数据
		server_count = ga.solve();  // 服务器的数量

		//填补（未优化）
		FillRestServer fillRestServer = new FillRestServer();
		server1 = fillRestServer.Fill(server, cpu, mem, server_count, target_f, re);

		HashMap<Integer, String> res_txt = new HashMap<Integer, String>();//哈希map，存储键值为：x号物理服务器(int)，flavorxx y个 flavorxxx yy个……，用于输出要求文本
		HashMap<String, Integer> hashMap = new HashMap<>();//key : vmname ,value:vmnum
		HashMap<String, Integer> res_map = new HashMap<>();
		String finalValue = "";   //res_txt的value值
		int tmp = 0; int imp = 0; // 临时变量
		int sum = 0;              // 剔除所有虚拟机的和
		for (int i = 1; i <= server_count; i++) {
			hashMap.clear();
			finalValue = "";
			for (int j = 1; j < server1[i].length; j++) {
				if (server1[i][j] != 0) {
					hashMap.put(target_f[j - 1], server1[i][j]);
				}
			}
			for (String key : hashMap.keySet()) {
				finalValue += key + " " + hashMap.get(key) + " ";
			}
			res_txt.put(i, finalValue);
		}

		//填补后修改预测值
		for (String key : freshmap.keySet()) {
			Predict.count += freshmap.get(key);
			for (int i = 0; i < target_f.length; i++) {
				if (key.equals(target_f[i])) {
					tmp = freshmap.get(key);
					target_f_num_qu[i] = tmp + target_f_num_qu[i];
				}

			}
		}

		//填补后修改预测值（最后一个服务器）
		for (String key : flavorsinlastserver.keySet()){
			for (int i = 0; i < target_f.length; i++){
				if (key.equals(target_f[i])){
					imp = flavorsinlastserver.get(key);
					sum += imp;
					target_f_num_qu[i] = target_f_num_qu[i] - imp;
				}
			}
		}

		int result_index = 0;//以下都是将数据输出
		target_num_all_qu = target_num_all_qu + Predict.count - sum;  //填补后的预测总数
		results[result_index] = target_num_all_qu + "\r";
		for (int j = 0; j < target_f.length; j++) {
			result_index++;
			results[result_index] = target_f[j] + " " + target_f_num_qu[j] + "\r";
		}
		result_index++;
		results[result_index] = "\r";
		result_index++;
		results[result_index] = String.valueOf(server_count);
		result_index++;

		Iterator iterator = res_txt.keySet().iterator();
		while (iterator.hasNext()) {
			int key = (int) iterator.next();
			results[result_index] = (key) + " " + res_txt.get(key) + "\r";
			result_index++;
		}

		//First Fit
//		int[] target_num=new int[target_f.length];//目标各规格虚拟机预测数量
//		for(int i=0;i<target_f.length;i++){
////			target_num[i]= (int) Math.ceil(target_f_num[i]);
//			target_num[i]= target_f_num_qu[i];
//		}
//		int server_num=1;//需要的物理服务器数量
//		int index=0;//记录当前需要分配的虚拟机索引
//		int[] server_cpu=new int[target_num_all_qu],server_mem=new int[target_num_all_qu];//代表服务器集群，每个都初始化成物理服务器参数，用server_num记录使用了哪些
//		for(int i=0;i<target_num_all_qu;i++){//初始化集群
//			server_cpu[i]=cpu;
//			server_mem[i]=mem;
//		}
//
//		boolean finish=false;//是否结束分配虚拟机标志
//		HashMap<String,Integer> res=new HashMap<String,Integer>();//哈希map，存储键值为：x号物理服务器，flavor规格号，代表一个flavor部署到x号服务器；此处是一个个部署，未做优化
//		HashMap<Integer,String> res_txt=new HashMap<Integer,String>();//哈希map，存储键值为：x号物理服务器(int)，flavorxx y个 flavorxxx yy个……，用于输出要求文本
//		while(!finish&&target_num_all_qu>0) {
//			int max_cpu = 0, max_mem = 0;
//			for (int i = 0; i < target_f.length; i++) {//找到最大参数的虚拟机
//				if (f_cpu[i] > max_cpu && f_mem[i] > max_mem && target_num[i] != 0) {
//					max_cpu = f_cpu[i];
//					max_mem = f_mem[i];
//					index = i;
//				}
//			}
//			int flag = 0;//当前搜索的物理服务器索引
//			for (; flag < server_num; flag++) {//找到可以分配的物理服务器
//				if (server_cpu[flag] - f_cpu[index] >= 0 && server_mem[flag] - f_mem[index] >= 0) {
//					server_cpu[flag] -= f_cpu[index];
//					server_mem[flag] -= f_mem[index];
//					target_num[index]--;
//					if (res.get(flag + " " + target_f[index]) != null) {
//						int tmp = res.get(flag + " " + target_f[index]);
//						res.put(flag + " " + target_f[index], tmp + 1);
//					} else {
//						res.put(flag + " " + target_f[index], 1);
//					}
//					break;
//				}
//			}
//			if (flag == server_num) {//如果flag超过sever_num，说明物理服务器不够，开辟新的服务器
//				server_num++;
//				server_cpu[flag] -= f_cpu[index];
//				server_mem[flag] -= f_mem[index];
//				target_num[index]--;
//				if (res.get(flag + " " + target_f[index]) != null) {
//					int tmp = res.get(flag + " " + target_f[index]);
//					res.put(flag + " " + target_f[index], tmp + 1);
//				} else {
//					res.put(flag + " " + target_f[index], 1);
//				}
//			}
//			finish = true;
//			for (int i = 0; i < target_f.length; i++) {//检查是否结束分配
//				if (target_num[i] != 0) {
//					finish = false;
//					break;
//				}
//			}
//		}
//		for(int i=0;i<=server_num;i++){//将分配的服务器以要求输出
//			Iterator it = res.keySet().iterator();
//			while(it.hasNext()) {
//				String key = (String)it.next();
//				String[] server_index=key.split(" ");
//				if(i==Integer.valueOf(server_index[0])){
//					if(res_txt.get(i)!=null){
//						String tmp=res_txt.get(i);
//						tmp=tmp+server_index[1]+" "+res.get(key)+" ";
//						res_txt.put(i,tmp);
//					}else{
//						res_txt.put(i,server_index[1]+" "+res.get(key)+" ");
//					}
//				}
////				System.out.println("key:" + server_index);
////				System.out.println("value:" + res.get(key));
//			}
//		}
//		int result_index=0;//以下都是将数据输出
//		results[result_index]=target_num_all_qu+"\r";
//		for(int i=0;i<target_f.length;i++){
//			result_index++;
////			results[result_index]=target_f[i]+" "+(int)Math.ceil(target_f_num[i])+"\r";
//			results[result_index]=target_f[i]+" "+target_f_num_qu[i]+"\r";
//		}
//		result_index++;
//		results[result_index]="\r";
//		result_index++;
//		if(server_num>0)
//			results[result_index]=server_num+"\r";
//		else
//			results[result_index]="0\r";
//		result_index++;
//		Iterator it = res_txt.keySet().iterator();
//		while(it.hasNext()) {
//			int key = (int) it.next();
//			results[result_index]=(key+1)+" "+res_txt.get(key)+"\r";
//			result_index++;
//		}
		return results;
	}

}
