package com.elasticcloudservice.predict;

/**
 * 填补虚拟机思路：
 *              1、对预测的虚拟机进行排序（cpu、mem）
 *              2、计算所有服务器的剩余空间（cpu 、 mem）
 *              3、对服务器进行填补，按虚拟机从大到小进行筛选
 *              4、对最后一个服务器做特殊处理：cpu(mem)的利用率低于50% 则删除该服务器，服务器内的虚拟机清空（待优化）
 *              5、修改预测虚拟机的数量
 */

import java.io.DataInput;
import java.io.IOException;
import java.util.*;

public class FillRestServer {

    int serverCPU = 0; int serverMEM = 0; int restCPU = 0; int restMEM = 0;
    /**
     * 填补
     * @param server 预测结果的保存格式 ：server[i][j] 第i台服务器j虚拟机的个数
     * @param sever_cpu 服务器cpu
     * @param server_mem 服务器mem
     * @param sever_count 预测出服务器的个数
     * @param flavor_name 预测虚拟机名字
     * @param map 虚拟机的规格
     * @return
     */
    public int[][] Fill(int[][] server, int sever_cpu, int server_mem, int sever_count,
                        String[] flavor_name,Map<String, SA.Flavor> map){
        int[][] data = new int[server.length][server[0].length];                //data[0][] 不用
        for (int i = 0; i < server.length; i++){
            for (int j = 0; j < server[0].length;j++){
                data[i][j] = server[i][j];
            }
        }

        serverCPU = sever_cpu; serverMEM = server_mem;                         // 服务器规格
        double cpu_usage_rate = 0; double mem_usage_rate =0;                   // cpu， mem未利用率
        //------根据预测虚拟机的cpu排序------//
        Vector<SA.Flavor> vector = new Vector<>();
        for (String key : map.keySet()){
            vector.add(map.get(key));
        }
        ComparatorImpl comparator = new ComparatorImpl();
        ComparatorImpl1 comparator1 = new ComparatorImpl1();

        //------遍历服务器计算剩余cpu和mem------//
        String name = ""; int num = 0; String[] temp; int v = 0; double tmp = 0;
        for (int i = 0; i < sever_count; i++){
            restCPU = sever_cpu; restMEM = server_mem;
            for (int j = 0; j < flavor_name.length; j++){
                if (map.get(flavor_name[j]) != null) {
                    restCPU = restCPU - server[i][j] * map.get(flavor_name[j]).cpu;
                    restMEM = restMEM - server[i][j] * map.get(flavor_name[j]).mem;
                }
            }
            if (restCPU == 0 || restMEM == 0){
                continue;
            }
            if ((double)restCPU / (restMEM/1024) > 1.0){
                Collections.sort(vector, comparator);       //cpu 排序
            }else {
                Collections.sort(vector,comparator1);       //mem 排序
            }
            for (int k = 0; k < vector.size(); k++) {
                if (restCPU >= vector.get(k).cpu && restMEM >= vector.get(k).mem){
                    num = (restCPU/vector.get(k).cpu) < (restMEM/vector.get(k).mem)
                            ? restCPU/vector.get(k).cpu : restMEM/vector.get(k).mem;    //填补的数量 待优化
                    name = vector.get(k).name;                                          //获取可以填补的虚拟机名
                    for (int l = 0; l < flavor_name.length; l++){
                        if (name.equals(flavor_name[l])) {                              // serevr[i][j] j是根据预测虚拟机的类别数
                            v = l;
//                            if (data[i][v] !=0) {
//                                if (num / data[i][v] > 0.1)
//                                    num = data[i][v] / 10;
//                            }
                            if (Predict.freshmap.get(name) != null)
                                Predict.freshmap.put(name, Predict.freshmap.get(name) + num);
                            else
                                Predict.freshmap.put(name,num);
                            while (num-- != 0) {
                                data[i][v] += 1;
                                restCPU = restCPU - map.get(name).cpu;
                                restMEM = restMEM - map.get(name).mem;
                            }
                        }
                    }
                }
            }
        }
        return data;
    }

    class ComparatorImpl implements Comparator<SA.Flavor> {
        public int compare(SA.Flavor s1,SA.Flavor s2) {
            int cpu1 = s1.cpu;
            int cpu2 = s2.cpu;
            if(cpu1<cpu2){
                return 1;
            }else if(cpu1>cpu2){
                return -1;
            }else{
                return 0;
            }
        }
    }

    class ComparatorImpl1 implements Comparator<SA.Flavor> {
        public int compare(SA.Flavor s1,SA.Flavor s2) {
            int mem1 = s1.mem;
            int mem2 = s2.mem;
            if(mem1<mem2){
                return 1;
            }else if(mem1>mem2){
                return -1;
            }else{
                return 0;
            }
        }
    }
}

