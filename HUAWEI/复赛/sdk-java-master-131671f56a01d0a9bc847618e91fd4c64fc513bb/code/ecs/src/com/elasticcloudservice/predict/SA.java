package com.elasticcloudservice.predict;

import javax.xml.bind.Element;
import java.lang.reflect.Field;
import java.security.Key;
import java.util.*;

public class SA {
    public SA(){

    }

    /**
     *
     * @param map_predict_num_flavors 要预测的虚拟机以其数量
     * @param map_flavor_cpu_mem 虚拟机的规格
     * @param server_mem 服务器内存
     * @param server_cpu 服务器cpu
     * @param CPUorMEM cpu择优还是mem择优
     * @return 服务器以及内存储存的虚拟机
     */
    public Vector<Server> put_flavor_to_servers(Map<String,Integer> map_predict_num_flavors, Map<String,Flavor> map_flavor_cpu_mem,
                                                int server_mem, int server_cpu, boolean CPUorMEM){
        Vector<Flavor> vec_flavors = new Vector<>();
        for (String key : map_predict_num_flavors.keySet()){
            int value = map_predict_num_flavors.get(key);
            while (value-- != 0){
                vec_flavors.add(map_flavor_cpu_mem.get(key));
            }
        }
        double min_server = vec_flavors.size() + 1;
        Vector<Server> res_servers = new Vector<>();
        double T = 100.0;
        double Tmin = 1;
        //double r = 0.9999;
        double r = 0.999;
        Vector<Integer> dice = new Vector<>();
        for (int i = 0; i < vec_flavors.size(); i++) {
            dice.add(i);
        }
        Vector<Server> temp = new Vector<>();
        Vector<Flavor> new_vec_flavors = new Vector<>();
        int totcpu = 0, totmem = 0, nextcpu = 0, nextmem = 0;
        int tmp = 0;
        while (T > Tmin){
            Collections.shuffle(dice);
            new_vec_flavors = vec_flavors;
            swap1(new_vec_flavors.get(dice.get((int) Math.random()*dice.size())),new_vec_flavors.get(dice.get((int)Math.random()*dice.size())));
           // swap1(new_vec_flavors.get(dice.get(5)),new_vec_flavors.get(dice.get(8)));
            Vector<Server> servers = new Vector<>();
            Server firstServer = new Server(server_mem,server_cpu);
            servers.add(firstServer);

            for (int i = 0; i < new_vec_flavors.size(); i++){
                Server server = servers.firstElement();
                totcpu = 0; totmem = 0;
                for (int j = 0; j < servers.size(); j++){
                    server = servers.elementAt(j);
                    if (servers.elementAt(j).put_flavor(new_vec_flavors.get(i))){
                        tmp = i;
                        break;
                    }
                }
                if (server == servers.lastElement()){
                    for (int  k = 0; k < server.flavors.size(); k++) {
                        totcpu += server.flavors.get(k).cpu;
                        totmem += server.flavors.get(k).mem;
                        if (tmp + 1  < new_vec_flavors.size()){
                            nextcpu = new_vec_flavors.get(tmp + 1).cpu;
                            nextmem = new_vec_flavors.get(tmp + 1).mem;
                        }
                        else {
                            nextcpu =0; nextmem = 0;
                        }
                    }
                }
                if ((totcpu + nextcpu) > server_cpu || (totmem + nextmem )> server_mem){
                    Server server1 = new Server(server_mem,server_cpu);
                    servers.add(server1);
                }
            }
            double server_num;

            if (CPUorMEM == true)
                server_num = servers.size() - 1 + servers.lastElement().get_cpu_usage_rate();//servers.rbegin()->get_cpu_usage_rate();
		    else
                server_num = servers.size() - 1 + servers.lastElement().get_mem_usage_rate();//servers.rbegin()->get_mem_usage_rate();

            if (server_num < min_server) {
                min_server = server_num;
                res_servers = servers;
                vec_flavors = new_vec_flavors;
            }
            else {
                if (Math.exp((min_server - server_num) / T) > Math.random()) {
                    min_server = server_num;
                    res_servers = servers;
                    vec_flavors = new_vec_flavors;
                }
            }
            T = r * T;
        }
        return res_servers;
    }

    public void swap1(Flavor a, Flavor b){
        if (a == null || b == null) {
            return;
        }
        Flavor c = new Flavor("",0,0);
        c = a;
        a = b;
        b = c;
    }

    public  void swap(Flavor a, Flavor b) {
        if (a == null || b == null) {
            return;
        }
        //获得a的class对象
        Class<Flavor> integerClass = (Class<Flavor>) a.getClass();
        try {
            //获得value属性
            Field name = integerClass.getDeclaredField("name");
            Field mem = integerClass.getDeclaredField("mem");
            Field cpu=integerClass.getDeclaredField("cpu");
            //设置权限为可访问
            name.setAccessible(true);
            mem.setAccessible(true);
            cpu.setAccessible(true);
            //交换
            String aname = a.name; int amem = a.mem; int acpu = a.cpu;
            name.set(a,b.name);name.set(b,aname);
            mem.set(a,b.mem); mem.set(b, amem);
            cpu.set(a,b.cpu); cpu.set(b,acpu);
            //Flavor temp=a;
            //value.set(a,b);
            //value.set(b,temp);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    class Server{

        public Vector<Flavor> flavors = new Vector<>();
        int total_mem;
        int total_cpu;
        int free_mem;
        int free_cpu;

        public Server(int mem, int cpu){
            total_mem = mem;
            total_cpu = cpu;
            free_cpu = cpu;  //初始化时剩余CPU等于总CPU
            free_mem = mem;  //初始化时剩余内存等于总内存
        }
        /*
        放置虚拟机函数，参数为虚拟机对象，返回值为是否放置成功
        首先检查剩余CPU和内存是否足够放置该虚拟机
        如果能够放下虚拟机，则将虚拟机放入服务器，并更新服务器可用内存和可用CPU，并返回true
        如果剩余内存和CPU不足以放下该虚拟机，则返回false
         */
        public Boolean put_flavor(Flavor flavor)
        {
            if (free_cpu >= flavor.cpu && free_mem >= flavor.mem) {
                free_cpu -= flavor.cpu;
                free_mem -= flavor.mem;
                flavors.add(flavor);
                return true;
            }
            return false;
        }
        public double get_cpu_usage_rate(){
            return (1.0 - (double) free_cpu / (double)total_cpu);
        }
        public double get_mem_usage_rate(){
            return (1.0 - (double) free_mem / (double)total_mem);
        }
    }

    class Flavor{
        public String name;
        public int mem;
        public int cpu;
        public Flavor(String name, int men, int cpu){
            this.name = name;
            this.mem = men;
            this.cpu = cpu;
        }
    }


}
