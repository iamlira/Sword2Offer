package com.elasticcloudservice.predict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GA {

    private int scale;              // 种群规模
    private int totalNum;           // 虚拟机总数
    private int Max_G;            // 运行代数
    private int bestT;              // 最佳出现代数
    private double bestLength;      // 最佳长度
    private int[] bestPut;         // 最佳放置方案

    private int[][] oldPopulation;  // 初始种群，父代种群，行数表示种群规模，一行代表一个个体，即染色体，列表示染色体基因片段
    private int[][] newPopulation;  // 新的种群，子代种群
    private double[] fitness;       // 种群适应度，表示种群中各个个体的适应度

    private float[] Pi;             // 种群中各个个体的累计概率
    private float cRate;            // 交叉概率
    private float mRate;            // 变异概率
    private int t;                  // 当前代数

    int[] cpu_data; int[] mem_data; HashMap<Integer, Integer> map; int serverCPU; int serverMEM; Random random;

    public GA(int scale, int num, int count, float pc, float pm,int serverCPU,int serverMEM,int[] CPU,int[] MEM,HashMap<Integer, Integer> map) {
        this.scale = scale; this.totalNum = num; this.Max_G = count;
        this.cRate = pc; this.mRate = pm;
        this.serverCPU = serverCPU; this.serverMEM = serverMEM;
        this.cpu_data = CPU; this.mem_data = MEM; this.map = map;
    }
    /**
     * 初始化值
     */
    public void Init(){
        bestLength = Double.MAX_VALUE;
        bestPut = new int[totalNum];
        bestT = 0;
        t = 0;
        newPopulation = new int[scale][totalNum];
        oldPopulation = new int[scale][totalNum];
        fitness = new double[scale];
        Pi = new float[scale];
        random = new Random(System.currentTimeMillis());
    }
    /**
     * 初始化种群
     */
    void InitGroup() {
        int i , j ;
        for (int k = 0; k < scale; k++)// 种群数
        {
            oldPopulation[k][0] = random.nextInt(65535) % totalNum;
            for (i = 1; i < totalNum;)// 染色体长度
            {
                oldPopulation[k][i] = random.nextInt(65535) % totalNum;
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

    public double EvaluateValue(int[] data) {
        double length = 0;
        int[] cpu = new int[totalNum];
        int[] mem = new int[totalNum];

        for (int i = 0; i < totalNum; i++) {
            cpu[i] = cpu_data[data[i]];
            mem[i] = mem_data[data[i]];
        }
        length = Count(cpu, mem);
        return length;
    }

    // 计算种群中各个个体的累积概率，前提是已经计算出各个个体的适应度fitness[max]，作为赌轮选择策略一部分，Pi[max]
    void CountRate() {
        int i;  double sumFitness = 0;// 适应度总和
        double[] tmp = new double[scale];
        for (i = 0; i < scale; i++) {
            tmp[i] = 10.0 / fitness[i];
            sumFitness += tmp[i];
        }
        Pi[0] = (float) (tmp[0] / sumFitness);
        for (i = 1; i < scale; i++) {
            Pi[i] = (float) (tmp[i] / sumFitness + Pi[i - 1]);
        }
    }

    //挑选某代种群中适应度最高的个体，直接复制到子代中  前提是已经计算出各个个体的适应度Fitness[max]
    public void SelectBestGh() {
        int max = 0; double maxevaluation;
        maxevaluation = Double.MAX_VALUE;
        for (int k = 1; k < scale; k++) {
            if (maxevaluation > fitness[k] && fitness[k] != 0) {
                maxevaluation = fitness[k];
                max = k;
            }
        }
        if (bestLength > maxevaluation) {
            bestLength = maxevaluation;
            bestT = t;// 最好的染色体出现的代数;
            for (int i = 0; i < totalNum; i++) {
                bestPut[i] = oldPopulation[max][i];
            }
        }
        CopyGh(0, max);// 将当代种群中适应度最高的染色体k复制到新种群中，排在第一位0
    }

    // 复制染色体，k表示新染色体在种群中的位置，kk表示旧的染色体在种群中的位置
    public void CopyGh(int j, int b) {
        for (int i = 0; i < totalNum; i++) {
            newPopulation[j][i] = oldPopulation[b][i];
        }
    }

    // 赌轮选择策略挑选
    public void Select() {
        int j, i, selectId; float r1;
        for (j = 1; j < scale; j++) {
            r1 = (float) (random.nextInt(65535) % 1000 / 1000.0);
            for (i = 0; i < scale; i++) {
                if (r1 <= Pi[i]) {
                    break;
                }
            }
            if(i == scale) i -= 1;
            selectId = i;
            CopyGh(j, selectId);
        }
    }

    //进化函数，正常交叉变异
    public void Evolution() {
        int k; float r;
        SelectBestGh();   // 挑选某代种群中适应度最高的个体
        Select();         // 赌轮选择策略挑选scale-1个下一代个体
        // 交叉方法
        for (k = 0; k < scale; k = k + 2) {
            r = random.nextFloat();        //产生概率
            if (r < cRate) {
                OXCross(k, k + 1);
            } else {
                r = random.nextFloat();    //产生概率
                // 变异
                if (r < mRate) {
                    OnCVariation(k);
                }
                r = random.nextFloat();   //产生概率
                if (r < mRate) {
                    OnCVariation(k + 1);
                }
            }

        }
    }

    // 交叉算子,相同染色体交叉产生不同子代染色体
    public void OXCross(int m1, int m2) {
        int i, j, k, flag;
        int ran1, ran2, temp;
        int[] Gh1 = new int[totalNum];
        int[] Gh2 = new int[totalNum];
        ran1 = random.nextInt(65535) % totalNum;
        ran2 = random.nextInt(65535) % totalNum;
        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % totalNum;
        }

        if (ran1 > ran2)// 确保ran1<ran2
        {
            temp = ran1;
            ran1 = ran2;
            ran2 = temp;
        }

        // 将染色体1中的第三部分移到染色体2的首部
        for (i = 0, j = ran2; j < totalNum; i++, j++) {
            Gh2[i] = newPopulation[m1][j];
        }

        flag = i;// 染色体2原基因开始位置

        for (k = 0, j = flag; j < totalNum;)// 染色体长度
        {
            Gh2[j] = newPopulation[m2][k++];
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
        for (k = 0, j = 0; k < totalNum;)// 染色体长度
        {
            Gh1[j] = newPopulation[m1][k++];
            for (i = 0; i < flag; i++) {
                if (newPopulation[m2][i] == Gh1[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        flag = totalNum - ran1;

        for (i = 0, j = flag; j < totalNum; j++, i++) {
            Gh1[j] = newPopulation[m2][i];
        }

        for (i = 0; i < totalNum; i++) {
            newPopulation[m1][i] = Gh1[i];// 交叉完毕放回种群
            newPopulation[m2][i] = Gh2[i];// 交叉完毕放回种群
        }
    }

    // 多次对换变异算子
    public void OnCVariation(int tmp) {
        int r1, r2, temp;
        int count;// 对换次数

        count = random.nextInt(65535) % totalNum;

        for (int i = 0; i < count; i++) {

            r1 = random.nextInt(65535) % totalNum;
            r2 = random.nextInt(65535) % totalNum;
            while (r1 == r2) {
                r2 = random.nextInt(65535) % totalNum;
            }
            temp = newPopulation[tmp][r1];
            newPopulation[tmp][r1] = newPopulation[tmp][r2];
            newPopulation[tmp][r2] = temp;
        }

    }

    public int GAFunction() {
        int server_count = 1; int i; int k;
        // 初始化种群
        InitGroup();
        // 计算初始化种群适应度，Fitness[max]
        for (k = 0; k < scale; k++) {
            // System.out.println(fitness[k]);
        }
        // 计算初始化种群中各个个体的累积概率，Pi[max]
        CountRate();
        for (t = 0; t < Max_G; t++) {
            //evolution1();
            Evolution();
            // 将新种群newGroup复制到旧种群oldGroup中，准备下一代进化
            for (k = 0; k < scale; k++) {
                for (i = 0; i < totalNum; i++) {
                    oldPopulation[k][i] = newPopulation[k][i];
                }
            }
            // 计算种群适应度
            for (k = 0; k < scale; k++) {
                fitness[k] = EvaluateValue(oldPopulation[k]);
            }
            CountRate();  //// 计算种群中各个个体的累积概率
        }
        while (true) {
            HashMap<Integer, Integer> countMap = new HashMap<>();
            int[] cpu = new int[totalNum];
            int[] mem = new int[totalNum];

            for (int j = 0; j < totalNum; j++) {

                cpu[j] = cpu_data[bestPut[j]];
                mem[j] = mem_data[bestPut[j]];
            }

            List<Object> list = SplitData(cpu, mem);
            int len = (int) list.get(list.size() - 1);
            if(len == 1) {
                countMap = new HashMap<>();
                for (int j = 0; j < totalNum; j++) {
                    if(countMap.get(map.get(bestPut[j])) == null) {
                        countMap.put(map.get(bestPut[j]), 1);
                    }else {
                        int time = countMap.get(map.get(bestPut[j]));
                        countMap.put(map.get(bestPut[j]), ++time);
                    }
                }
                PutFlavors(countMap, server_count);

                return server_count;
            }else {
                for(int j = 0;j < len;j++) {
                    countMap = new HashMap<>();
                    server_count = j+1;
                    if(j == 0) {
                        int fir = 0;
                        int split = (int) list.get(j);

                        for(;fir < split;fir++) {
                            if(!countMap.containsKey(map.get(bestPut[fir]))) {
                                countMap.put(map.get(bestPut[fir]), 1);
                            }else {
                                int time = countMap.get(map.get(bestPut[fir]));
                                countMap.put(map.get(bestPut[fir]), ++time);
                            }
                        }
                        PutFlavors(countMap, server_count);
                    }else if(j == len - 1){
                        System.out.println("------------");
                        countMap = new HashMap<>();
                        int fir = (int) list.get(j - 1);
                        for(;fir < totalNum;fir++) {
                            if(!countMap.containsKey(map.get(bestPut[fir]))) {
                                countMap.put(map.get(bestPut[fir]), 1);
                            }else {
                                int time = countMap.get(map.get(bestPut[fir]));
                                countMap.put(map.get(bestPut[fir]), ++time);
                            }
                        }
                        PutFlavors(countMap, server_count);

                        return server_count;
                    }else {
                        countMap = new HashMap<>();
                        int fir = (int) list.get(j - 1);
                        int next = (int) list.get(j);

                        for(;fir < next;fir++) {
                            if(countMap.get(map.get(bestPut[fir])) == null) {
                                countMap.put(map.get(bestPut[fir]), 1);
                            }else {
                                int time = countMap.get(map.get(bestPut[fir]));
                                countMap.put(map.get(bestPut[fir]), ++time);
                            }
                        }
                        PutFlavors(countMap, server_count);
                    }
                }
            }
        }
    }

    private double Count(int[] CPU,int[] MEM) {
        double total_cpu = 0;
        double total_mem = 0;
        double num = 0;
        List<Object> lists = SplitData(CPU, MEM);
        int length = (int) lists.get(lists.size() - 1);
        if(length == 1) {
            return 1;
        }else {
            int fir = (int) lists.get(length - 2);
            for(;fir < totalNum;fir++) {
                if(Predict.type.equals("CPU")) {
                    total_cpu += CPU[fir];
                }
                if(Predict.type.equals("MEM")) {
                    total_mem += MEM[fir];
                }
            }
            if(Predict.type.equals("CPU")) {
                num = total_cpu / serverCPU + length - 1;
                return num;
            }
            if(Predict.type.equals("MEM")) {
                num = total_mem / serverMEM  + length - 1;
                return num;
            }
        }
        return num;
    }

    private List<Object> SplitData(int[] CPU,int[] MEM){
        List<Object> list = new ArrayList<>();
        int count = 1;
        int cpuNum = serverCPU;
        int memNum = serverMEM;

        for(int i = 0;i < totalNum;i++) {
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

    private void PutFlavors(HashMap<Integer, Integer> countMap,int server_count) {
        for(Integer integer : countMap.keySet()) {
            Predict.server[server_count][integer] = countMap.get(integer);
        }
    }

}