package com.elasticcloudservice.predict;
import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;  
  
public class GA {  
  
    private int scale;              // 种群规模
    private int Num;                // 虚拟机总数
    private int MAX_GEN;            // 运行代数
    private int bestT;              // 最佳出现代数
    private double bestLength;      // 最佳长度
    private int[] bestTour;         // 最佳放置方案
  

    private int[][] oldPopulation;  // 初始种群，父代种群，行数表示种群规模，一行代表一个个体，即染色体，列表示染色体基因片段
    private int[][] newPopulation;  // 新的种群，子代种群
    private double[] fitness;       // 种群适应度，表示种群中各个个体的适应度
  
    private float[] Pi;             // 种群中各个个体的累计概率
    private float Pc;               // 交叉概率
    private float Pm;               // 变异概率
    private int t;                  // 当前代数
    
    int[] cpu_data; int[] mem_data;
    HashMap<Integer, Integer> map;
    int serverCPU;
    int serverMEM;
    Random random;

    public GA(int scale, int n, int g, float c, float m,int CPU_NUM,int MEM_NUM,int[] CPU,int[] MEM,HashMap<Integer, Integer> map) {
        this.scale = scale;
        Num = n;  
        MAX_GEN = g;  
        Pc = c;  
        Pm = m;  
        this.serverCPU = CPU_NUM;
        this.serverMEM = MEM_NUM;
        this.cpu_data = CPU;
        this.mem_data = MEM;
        this.map = map;
    }
    /**
     * 初始化值
     */
    public void init(){
        bestLength = Double.MAX_VALUE;
        bestTour = new int[Num];  
        bestT = 0;  
        t = 0;
        newPopulation = new int[scale][Num];  
        oldPopulation = new int[scale][Num];  
        fitness = new double[scale];  
        Pi = new float[scale];
        random = new Random(System.currentTimeMillis());  
  
    }
    /**
     * 初始化种群
      */
    void initGroup() {  
        int i, j, k;
        for (k = 0; k < scale; k++)// 种群数  
        {  
            oldPopulation[k][0] = random.nextInt(65535) % Num;  
            for (i = 1; i < Num;)// 染色体长度  
            {  
                oldPopulation[k][i] = random.nextInt(65535) % Num;  
                for (j = 0; j < i; j++) {  
                    if (oldPopulation[k][i] == oldPopulation[k][j]) {  
                        break;  
                    }  
                }  
                if (j == i) {  
                    i++;  
                }  
            }  
        }
    }  
  
    public double evaluate(int[] chromosome) {
    	double len = 0;  
        int[] NEW_CPU = new int[Num];
        int[] NEW_MEM = new int[Num];
 
        for (int i = 0; i < Num; i++) {  
        	NEW_CPU[i] = cpu_data[chromosome[i]];
        	NEW_MEM[i] = mem_data[chromosome[i]];
        }  
        len = count(NEW_CPU, NEW_MEM);

        return len;  
    }  
  
    // 计算种群中各个个体的累积概率，前提是已经计算出各个个体的适应度fitness[max]，作为赌轮选择策略一部分，Pi[max]  
    void countRate() {  
        int k;  
        double sumFitness = 0;// 适应度总和  
  
        double[] tempf = new double[scale];  
  
        for (k = 0; k < scale; k++) {  
            tempf[k] = 10.0 / fitness[k];  
            sumFitness += tempf[k];  
        }  
  
        Pi[0] = (float) (tempf[0] / sumFitness);  
        for (k = 1; k < scale; k++) {  
            Pi[k] = (float) (tempf[k] / sumFitness + Pi[k - 1]);  
        }
    }  

    //挑选某代种群中适应度最高的个体，直接复制到子代中  前提是已经计算出各个个体的适应度Fitness[max]
    public void selectBestGh() {  
        int k, i, maxid;  
        double maxevaluation;
        maxid = 0;  
        maxevaluation = Double.MAX_VALUE;  
        for (k = 1; k < scale; k++) {  
            if (maxevaluation > fitness[k] && fitness[k] != 0) { 
                maxevaluation = fitness[k]; 
                maxid = k;  
            }  
        }
        if (bestLength > maxevaluation) {  
            bestLength = maxevaluation;  
            bestT = t;// 最好的染色体出现的代数;  
            for (i = 0; i < Num; i++) {  
                bestTour[i] = oldPopulation[maxid][i];  
                System.out.print(bestTour[i] + ",");
            }  
        }
        copyGh(0, maxid);// 将当代种群中适应度最高的染色体k复制到新种群中，排在第一位0  
    }  
  
    // 复制染色体，k表示新染色体在种群中的位置，kk表示旧的染色体在种群中的位置  
    public void copyGh(int k, int kk) {  
        int i;  
        for (i = 0; i < Num; i++) {  
            newPopulation[k][i] = oldPopulation[kk][i];  
        }  
    }  
  
    // 赌轮选择策略挑选  
    public void select() {  
        int k, i, selectId;  
        float ran1;
        for (k = 1; k < scale; k++) {  
            ran1 = (float) (random.nextInt(65535) % 1000 / 1000.0);
            for (i = 0; i < scale; i++) {  
                if (ran1 <= Pi[i]) {  
                    break;  
                }  
            }  
            if(i == scale) i -= 1;
            selectId = i;
            copyGh(k, selectId);  
        }  
    }  
  
    //进化函数，正常交叉变异  
    public void evolution() {  
        int k;
        selectBestGh();   // 挑选某代种群中适应度最高的个体
        select();         // 赌轮选择策略挑选scale-1个下一代个体
        float r;
        // 交叉方法  
        for (k = 0; k < scale; k = k + 2) {  
            r = random.nextFloat();        //产生概率
            if (r < Pc) {
                OXCross1(k, k + 1);  
            } else {  
                r = random.nextFloat();    //产生概率
                // 变异  
                if (r < Pm) {
                    OnCVariation(k);  
                }  
                r = random.nextFloat();   //产生概率
                if (r < Pm) {
                    OnCVariation(k + 1);  
                }  
            }  
  
        }  
    }  
  
    //进化函数，保留最好染色体不进行交叉变异  
    public void evolution1() {  
        int k;
        selectBestGh();  // 挑选某代种群中适应度最高的个体
        select();        // 赌轮选择策略挑选scale-1个下一代个体
        float r;  
  
        for (k = 1; k + 1 < scale / 2; k = k + 2) {  
            r = random.nextFloat();// /产生概率  
            if (r < Pc) {  
                OXCross1(k, k + 1);// 进行交叉
            } else {  
                r = random.nextFloat();//产生概率
                // 变异  
                if (r < Pm) {  
                    OnCVariation(k);  
                }  
                r = random.nextFloat();//产生概率
                // 变异  
                if (r < Pm) {  
                    OnCVariation(k + 1);  
                }  
            }  
        }  
        if (k == scale / 2 - 1)// 剩最后一个染色体没有交叉L-1  
        {  
            r = random.nextFloat();// /产生概率  
            if (r < Pm) {  
                OnCVariation(k);  
            }  
        }  
  
    }  
  
    // 类OX交叉算子  
    void OXCross(int k1, int k2) {  
        int i, j, k, flag;  
        int ran1, ran2, temp;  
        int[] Gh1 = new int[Num];  
        int[] Gh2 = new int[Num];
  
        ran1 = random.nextInt(65535) % Num;  
        ran2 = random.nextInt(65535) % Num;
  
        while (ran1 == ran2) {  
            ran2 = random.nextInt(65535) % Num;  
        }  
  
        if (ran1 > ran2)// 确保ran1<ran2  
        {  
            temp = ran1;  
            ran1 = ran2;  
            ran2 = temp;  
        }
        flag = ran2 - ran1 + 1;// 删除重复基因前染色体长度  
        for (i = 0, j = ran1; i < flag; i++, j++) {  
            Gh1[i] = newPopulation[k2][j];  
            Gh2[i] = newPopulation[k1][j];  
        }  
        // 已近赋值i=ran2-ran1个基因
        for (k = 0, j = flag; j < Num;)// 染色体长度  
        {  
            Gh1[j] = newPopulation[k1][k++];  
            for (i = 0; i < flag; i++) {  
                if (Gh1[i] == Gh1[j]) {  
                    break;  
                }  
            }  
            if (i == flag) {  
                j++;  
            }  
        }  
  
        for (k = 0, j = flag; j < Num;)// 染色体长度  
        {  
            Gh2[j] = newPopulation[k2][k++];  
            for (i = 0; i < flag; i++) {  
                if (Gh2[i] == Gh2[j]) {  
                    break;  
                }  
            }  
            if (i == flag) {  
                j++;  
            }  
        }  
  
        for (i = 0; i < Num; i++) {  
            newPopulation[k1][i] = Gh1[i];// 交叉完毕放回种群  
            newPopulation[k2][i] = Gh2[i];// 交叉完毕放回种群  
        }
    }  
  
    // 交叉算子,相同染色体交叉产生不同子代染色体  
    public void OXCross1(int k1, int k2) {  
        int i, j, k, flag;  
        int ran1, ran2, temp;  
        int[] Gh1 = new int[Num];  
        int[] Gh2 = new int[Num];
        ran1 = random.nextInt(65535) % Num;  
        ran2 = random.nextInt(65535) % Num;  
        while (ran1 == ran2) {  
            ran2 = random.nextInt(65535) % Num;  
        }  
  
        if (ran1 > ran2)// 确保ran1<ran2  
        {  
            temp = ran1;  
            ran1 = ran2;  
            ran2 = temp;  
        }  
  
        // 将染色体1中的第三部分移到染色体2的首部  
        for (i = 0, j = ran2; j < Num; i++, j++) {  
            Gh2[i] = newPopulation[k1][j];  
        }  
  
        flag = i;// 染色体2原基因开始位置  
  
        for (k = 0, j = flag; j < Num;)// 染色体长度  
        {  
            Gh2[j] = newPopulation[k2][k++];  
            for (i = 0; i < flag; i++) {  
                if (Gh2[i] == Gh2[j]) {  
                    break;  
                }  
            }  
            if (i == flag) {  
                j++;  
            }  
        }  
  
        flag = ran1;  
        for (k = 0, j = 0; k < Num;)// 染色体长度  
        {  
            Gh1[j] = newPopulation[k1][k++];  
            for (i = 0; i < flag; i++) {  
                if (newPopulation[k2][i] == Gh1[j]) {  
                    break;  
                }  
            }  
            if (i == flag) {  
                j++;  
            }  
        }  
  
        flag = Num - ran1;  
  
        for (i = 0, j = flag; j < Num; j++, i++) {  
            Gh1[j] = newPopulation[k2][i];  
        }  
  
        for (i = 0; i < Num; i++) {  
            newPopulation[k1][i] = Gh1[i];// 交叉完毕放回种群  
            newPopulation[k2][i] = Gh2[i];// 交叉完毕放回种群  
        }  
    }  
  
    // 多次对换变异算子  
    public void OnCVariation(int k) {  
        int ran1, ran2, temp;  
        int count;// 对换次数  

        count = random.nextInt(65535) % Num;  
  
        for (int i = 0; i < count; i++) {  
  
            ran1 = random.nextInt(65535) % Num;  
            ran2 = random.nextInt(65535) % Num;  
            while (ran1 == ran2) {  
                ran2 = random.nextInt(65535) % Num;  
            }  
            temp = newPopulation[k][ran1];  
            newPopulation[k][ran1] = newPopulation[k][ran2];  
            newPopulation[k][ran2] = temp;  
        }  

    }  
  
    public int solve() {  
    	int server_count = 1;
    	
        int i;  
        int k;  
  
        // 初始化种群  
        initGroup();  
        // 计算初始化种群适应度，Fitness[max]  
        for (k = 0; k < scale; k++) {  
            // System.out.println(fitness[k]);  
        }  
        // 计算初始化种群中各个个体的累积概率，Pi[max]  
        countRate();
          
        for (t = 0; t < MAX_GEN; t++) {  
            //evolution1();  
            evolution();
            // 将新种群newGroup复制到旧种群oldGroup中，准备下一代进化  
            for (k = 0; k < scale; k++) {  
                for (i = 0; i < Num; i++) {  
                    oldPopulation[k][i] = newPopulation[k][i];  
                }  
            }  
            // 计算种群适应度  
            for (k = 0; k < scale; k++) {  
                fitness[k] = evaluate(oldPopulation[k]);  
            }  
            // 计算种群中各个个体的累积概率  
            countRate();  
        }
 
        System.out.println("最佳路径：");  
        for (i = 0; i < Num; i++) {  
            System.out.print(bestTour[i] + ",");  
        }  
        System.out.println();
        
        while (true) {
        	HashMap<Integer, Integer> countMap = new HashMap<>();
        	
   	        int size = 0;
   	        
   	        int[] NEW_CPU = new int[Num];
   	        int[] NEW_MEM = new int[Num];
   	        
   	        for (int j = 0; j < Num; j++) {  

   	        	NEW_CPU[j] = cpu_data[bestTour[j]];
   	        	NEW_MEM[j] = mem_data[bestTour[j]];
   	        }
   	        
   	     List<Object> list = split(NEW_CPU, NEW_MEM);
   	     int len = (int) list.get(list.size() - 1);
   	     if(len == 1) {
   	    	countMap = new HashMap<>();
   	    	for (int j = 0; j < Num; j++) {
   	    		if(countMap.get(map.get(bestTour[j])) == null) {
                	countMap.put(map.get(bestTour[j]), 1);
                }else {
                	int time = countMap.get(map.get(bestTour[j]));
                	countMap.put(map.get(bestTour[j]), ++time);
                }
   	    	}
   	    	put(countMap, server_count);
   	    	
   	    	return server_count;
   	     }else {
			for(int j = 0;j < len;j++) {
				countMap = new HashMap<>();
				server_count = j+1;
				if(j == 0) {
				   int fir = 0;
				   int split = (int) list.get(j);
				   
				   for(;fir < split;fir++) {
					   if(!countMap.containsKey(map.get(bestTour[fir]))) {
						   countMap.put(map.get(bestTour[fir]), 1);
					   }else {
						   int time = countMap.get(map.get(bestTour[fir]));
						   countMap.put(map.get(bestTour[fir]), ++time);
					}
				   }
				   put(countMap, server_count);
				}else if(j == len - 1){
					System.out.println("------------");
					countMap = new HashMap<>();
					int fir = (int) list.get(j - 1);
					for(;fir < Num;fir++) {
						if(!countMap.containsKey(map.get(bestTour[fir]))) {
							   countMap.put(map.get(bestTour[fir]), 1);
						   }else {
							   int time = countMap.get(map.get(bestTour[fir]));
							   countMap.put(map.get(bestTour[fir]), ++time);
						}
					}
					put(countMap, server_count);
					
					return server_count;
				}else {
					countMap = new HashMap<>();
					int fir = (int) list.get(j - 1);
					int next = (int) list.get(j);
					
					for(;fir < next;fir++) {
						if(countMap.get(map.get(bestTour[fir])) == null) {
							   countMap.put(map.get(bestTour[fir]), 1);
						   }else {
							   int time = countMap.get(map.get(bestTour[fir]));
							   countMap.put(map.get(bestTour[fir]), ++time);
						}
					}
					
					put(countMap, server_count);
				}
			}
		}
		}
       
    }  
    
    //返回需要的物理机数量
    private double count(int[] CPU,int[] MEM) {
    	double total_cpu = 0;
    	double total_mem = 0;
    	List<Double> list = new ArrayList<>();
    	
    	double count = 0;
    	int cpuNum = serverCPU;
    	int memNum = serverMEM;

    	List<Object> lists = split(CPU, MEM);
    	int len = (int) lists.get(lists.size() - 1);
    	if(len == 1) {
    		return 1;
    	}else {
			int fir = (int) lists.get(len - 2);
			for(;fir < Num;fir++) {
				if(Predict.type.equals("CPU")) {
					total_cpu += CPU[fir];
				}
				if(Predict.type.equals("MEM")) {
					total_mem += MEM[fir];
				}
			}
			
			if(Predict.type.equals("CPU")) {
				count = total_cpu / serverCPU + len - 1;
				return count;
			}
			
			if(Predict.type.equals("MEM")) {
				count = total_mem / serverMEM  + len - 1;
				return count;
			}
		}
    	
    	
    	return count;
    }  
    
    private List<Object> split(int[] CPU,int[] MEM){
    	List<Object> list = new ArrayList<>();
    	int count = 1;
    	int cpuNum = serverCPU;
    	int memNum = serverMEM;
    	
    	for(int i = 0;i < Num;i++) {
    		if(cpuNum >= CPU[i] && memNum >= MEM[i]) {
    			cpuNum -= CPU[i];
    			memNum -= MEM[i];
    		}else {
    			list.add(i);
				count++;
				cpuNum = serverCPU;
				memNum = serverMEM;
				cpuNum -= CPU[i];
    			memNum -= MEM[i];
			}
    	}
    	list.add(count);
    	return list;
    	
    }
    
    private void put(HashMap<Integer, Integer> countMap,int server_count) {
        for(Integer integer : countMap.keySet()) {
        	Predict.server[server_count][integer] = countMap.get(integer);
        }
    }
  
} 
