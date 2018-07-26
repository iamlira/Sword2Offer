package com.elasticcloudservice.predict;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lira on 2018/4/25.
 */
public class Allocation {
    double general_c_m,highp_c_m,largem_c_m,cur_c_m;
    double sum_cpu,sum_mem;
    static final int GENERAL=0,LARGEM=1,HIGHP=2,IDLECPU=0,IDLEMEM=1,NOTFOUND=999;
    int numOfServerType[],cpuOfServerType[],memOfServerType[];
    int startIndexOfGeneral, startIndexOfLargeMEM, startIndexOfHighPerform;  //存放三种服务器在队列中的起始位置
    int[] pre_flavor,f_cpu,f_mem;
    int idleResource[];
    public void init(int[] pre_flavor,int[] f_cpu,int[] f_mem,int servertype,int[] server_cpu,int[] server_mem){
        numOfServerType=new int[servertype];
        cpuOfServerType=new int[server_cpu.length];
        memOfServerType=new int[server_mem.length];
        idleResource=new int[2];
        this.pre_flavor=pre_flavor.clone();
        this.f_cpu=new int[f_cpu.length];
        this.f_mem=new int[f_mem.length];
        cpuOfServerType=server_cpu.clone();
        memOfServerType=server_mem.clone();
        for(int i=0;i<f_cpu.length;i++){
            this.f_cpu[i]=f_cpu[i];
            this.f_mem[i]=f_mem[i]/1024;
        }
//        this.f_cpu=f_cpu.clone();
//        this.f_mem=f_mem.clone();
        for(int i=0;i<pre_flavor.length;i++){
            sum_cpu+=pre_flavor[i]*f_cpu[i];
            sum_mem+=pre_flavor[i]*this.f_mem[i];
        }
        general_c_m=(double)server_cpu[GENERAL]/(double)server_mem[GENERAL];
        highp_c_m=(double)server_cpu[HIGHP]/(double)server_mem[HIGHP];
        largem_c_m=(double)server_cpu[LARGEM]/(double)server_mem[LARGEM];
        cur_c_m=sum_cpu/sum_mem;
    }
    public int[][] allocate(){
	/*获取最优的服务器组合*/
        GetServerTypeNum();

	/*开始分配*/
        return PutFlavorToServers();
    }
    public void GetServerTypeNum(){
        /*CPU数量全面压倒*/
        if(cur_c_m >= highp_c_m)
        {
            numOfServerType[GENERAL] = 0;
            numOfServerType[LARGEM] = 0;
            numOfServerType[HIGHP] = (int)Math.floor(sum_cpu/(double)cpuOfServerType[HIGHP] + 0.5);
            if(numOfServerType[HIGHP] * cpuOfServerType[HIGHP] < sum_cpu)
            {
                numOfServerType[GENERAL]++;
            }
            return;
        }

	/*内存数量全面压倒*/
        if(cur_c_m <= largem_c_m)
        {
            numOfServerType[GENERAL] = 0;
            numOfServerType[LARGEM] = (int)Math.floor(sum_mem/(double)memOfServerType[LARGEM] + 0.5);
            numOfServerType[HIGHP] = 0;
            if(numOfServerType[LARGEM] * memOfServerType[LARGEM] < sum_mem)
            {
                numOfServerType[GENERAL]++;
            }
            return;
        }

	/*介于之间*/
	/*根据General类型确定初始数量*/
        int tempNumForCPU = (int)Math.ceil(sum_cpu/(double)cpuOfServerType[GENERAL]);
        int tempNumForMEM = (int)Math.ceil(sum_mem/(double)memOfServerType[GENERAL]);
        numOfServerType[GENERAL] = tempNumForCPU>tempNumForMEM?tempNumForCPU:tempNumForMEM;
        numOfServerType[LARGEM] = 0;
        numOfServerType[HIGHP] = 0;
	/*逐步减少内存*/
        if(cur_c_m > general_c_m)
        {
            AdjustServerNumForMEM(sum_mem);
        }
	/*逐步减少CPU*/
        if(cur_c_m < general_c_m)
        {
            AdustServerNumForCPU(sum_cpu);
        }

        return;
    }
    /*整服务器减少MEM数量*/
    void AdjustServerNumForMEM(double needMEM)
    {
        int reduceMEM = 2 * memOfServerType[GENERAL] - memOfServerType[HIGHP];
        int SumMEM = numOfServerType[GENERAL] * memOfServerType[GENERAL] +
                numOfServerType[HIGHP] * memOfServerType[HIGHP] +
                numOfServerType[LARGEM] * memOfServerType[LARGEM];

        while ((SumMEM - reduceMEM) >= needMEM && numOfServerType[GENERAL] >= 2)
        {
            numOfServerType[GENERAL] -= 2;
            numOfServerType[HIGHP] ++;
            SumMEM -= reduceMEM;
        }
        idleResource[IDLEMEM] = SumMEM;
    }

    /*整服务器减少CPU数量*/
    void AdustServerNumForCPU(double needCPU)
    {
        int reduceCPU = 2 * cpuOfServerType[GENERAL] - cpuOfServerType[LARGEM];
        int SumCPU = numOfServerType[GENERAL] * cpuOfServerType[GENERAL] +
                numOfServerType[HIGHP] * cpuOfServerType[HIGHP] +
                numOfServerType[LARGEM] * cpuOfServerType[LARGEM];

        while ((SumCPU - reduceCPU) >= needCPU && numOfServerType[GENERAL] >= 2)
        {
            numOfServerType[GENERAL] -= 2;
            numOfServerType[LARGEM] ++;
            SumCPU -= reduceCPU;
        }
        idleResource[IDLECPU] = SumCPU;
    }
    public int[][] PutFlavorToServers(){
        startIndexOfGeneral = 0;
        startIndexOfLargeMEM = startIndexOfGeneral + numOfServerType[GENERAL];
        startIndexOfHighPerform = startIndexOfLargeMEM + numOfServerType[LARGEM];
        //int[][] flavorInServer=new int[numOfServerType[GENERAL]+numOfServerType[HIGHP]+numOfServerType[LARGEM]][pre_flavor.length];
        List<int[]> flavorInServer=new ArrayList<>();
        initServerList(flavorInServer);

//        int[] remainCpu=new int[numOfServerType[GENERAL]+numOfServerType[HIGHP]+numOfServerType[LARGEM]];
//        int[] remainMem=new int[numOfServerType[GENERAL]+numOfServerType[HIGHP]+numOfServerType[LARGEM]];
        List<Integer> remainCpu=new ArrayList<>();
        List<Integer> remainMem=new ArrayList<>();
        initRemainList(remainCpu,remainMem);
//        for(int i=startIndexOfGeneral;i<startIndexOfLargeMEM;i++){
//            remainCpu[i]=cpuOfServerType[GENERAL];
//            remainMem[i]=memOfServerType[GENERAL];
//        }
//        for(int i=startIndexOfLargeMEM;i<startIndexOfHighPerform;i++){
//            remainCpu[i]=cpuOfServerType[LARGEM];
//            remainMem[i]=memOfServerType[LARGEM];
//        }
//        for(int i=startIndexOfHighPerform;i<remainCpu.length;i++){
//            remainCpu[i]=cpuOfServerType[HIGHP];
//            remainMem[i]=memOfServerType[HIGHP];
//        }
        for(int i=pre_flavor.length-1;i>=0;i--){
            for(int j=0;j<pre_flavor[i];j++){
                double minDif = Double.MAX_VALUE;
                int serverIndex = NOTFOUND;
                double flavorRate = (double)f_cpu[i] / (double)f_mem[i];
                /*计算cup内存比例最接近的服务器*/
                for(int k = 0; k < numOfServerType[GENERAL]+numOfServerType[HIGHP]+numOfServerType[LARGEM]; k++)
                {
				/*先判断资源是否足够*/
                    if(remainCpu.get(k) < f_cpu[i] || remainMem.get(k) < f_mem[i])
                        continue;

                    double serverRate = (double)remainCpu.get(k) / (double)remainMem.get(k);
                    double tempDif = serverRate - flavorRate;
                    tempDif = tempDif * tempDif;
                    if(tempDif < minDif)
                    {
                        minDif = tempDif;
                        serverIndex = k;
                    }
                }
                if(serverIndex!=NOTFOUND) {
//                    flavorInServer[serverIndex][i]++;
                    flavorInServer.get(serverIndex)[i]++;
                    remainCpu.set(serverIndex,remainCpu.get(serverIndex)-f_cpu[i]);
                    remainMem.set(serverIndex,remainMem.get(serverIndex)-f_mem[i]);
                }else{
                    AddServer(flavorInServer,remainCpu,remainMem);
                    flavorInServer.get(flavorInServer.size()-1)[i]++;
                    remainCpu.set(remainCpu.size()-1,remainCpu.get(remainCpu.size()-1)-f_cpu[i]);
                    remainMem.set(remainMem.size()-1,remainMem.get(remainMem.size()-1)-f_mem[i]);
//                    int remainSumCpu=0,remainSumMem=0;
//                    for(int z=0;z<flavorInServer.length;z++){
//                        remainSumCpu+=sum_cpu-flavorInServer[z][i]*f_cpu[i];
//                        remainSumMem+=sum_mem-flavorInServer[z][i]*f_mem[i];
//                    }
//                    double remainSum_c_m=(double)remainSumCpu/(double)remainSumMem;
//                    if(remainSum_c_m>=highp_c_m){
//                        numOfServerType[HIGHP]++;
//
//                    }else if(remainSum_c_m<=largem_c_m){
//
//                    }else{
//
//                    }
                }

            }
        }
        return List2Array(flavorInServer);
    }
    public void initServerList(List<int[]> flavorInServer){
        for(int i=0;i<numOfServerType[GENERAL]+numOfServerType[HIGHP]+numOfServerType[LARGEM];i++){
            int[] oneFlavor=new int[pre_flavor.length];
            flavorInServer.add(oneFlavor);
        }
    }
    public void initRemainList(List<Integer> remainCpu,List<Integer> remainMem){
        for(int i=startIndexOfGeneral;i<startIndexOfLargeMEM;i++){
            remainCpu.add(cpuOfServerType[GENERAL]);
            remainMem.add(memOfServerType[GENERAL]);
        }
        for(int i=startIndexOfLargeMEM;i<startIndexOfHighPerform;i++){
            remainCpu.add(cpuOfServerType[LARGEM]);
            remainMem.add(memOfServerType[LARGEM]);
        }
        for(int i=startIndexOfHighPerform;i<numOfServerType[GENERAL]+numOfServerType[HIGHP]+numOfServerType[LARGEM];i++){
            remainCpu.add(cpuOfServerType[HIGHP]);
            remainMem.add(memOfServerType[HIGHP]);
        }
    }
    public void AddServer(List<int[]> flavorInServer,List<Integer> remainCpu,List<Integer> remainMem){
        int[] newServer=new int[pre_flavor.length];
        flavorInServer.add(newServer);
        remainCpu.add(cpuOfServerType[GENERAL]);
        remainMem.add(memOfServerType[GENERAL]);
        numOfServerType[HIGHP]++;
//        int[][] flavorInServer_tmp=new int[flavorInServer.length+1][flavorInServer[0].length];
//        int[] remainCpu_tmp=new int[remainCpu.length+1];
//        int[] remainMem_tmp=new int[remainMem.length+1];
//        switch (TYPE){
//            case GENERAL:
//                break;
//            case LARGEM:
//                break;
//            case HIGHP:
//                break;
//        }
//        for(int i=0;i<flavorInServer.length;i++){
//            flavorInServer_tmp[i]=flavorInServer[i].clone();
//        }
//        flavorInServer=flavorInServer_tmp;
//        for(int i=0;i<remainCpu.length;i++){
//            remainCpu_tmp[i]=remainCpu[i];
//            remainCpu_tmp[i]=remainMem[i];
//        }
//        remainCpu=remainCpu_tmp;
//        remainMem=remainMem_tmp;
    }
    public int[][] List2Array(List<int[]> flavorInServer){
        int[][] flavorInServerArray=new int[flavorInServer.size()][flavorInServer.get(0).length];
        for(int i=0;i<flavorInServerArray.length;i++){
            for(int j=0;j<flavorInServerArray[0].length;j++){
                flavorInServerArray[i][j]=flavorInServer.get(i)[j];
            }
        }
        return flavorInServerArray;
    }
}
