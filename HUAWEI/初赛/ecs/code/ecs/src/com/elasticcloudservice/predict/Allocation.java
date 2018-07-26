package com.elasticcloudservice.predict;

/**
 * Created by lira on 2018/3/22.
 */
public class Allocation {
    public Allocation(){
        dataTransform=new DataTransform();
        FLAVOR_MAX_NUM=100;
        PredictFlavor=new ClassPredictFlavor[this.FLAVOR_MAX_NUM];
        Greedy=new ClassGreedy[this.FLAVOR_MAX_NUM];
        Priority=new int[FLAVOR_MAX_NUM][2];           
        output=new Output();
        inputData=new InputData();
        inputData.flavorList=new Flavor[this.FLAVOR_MAX_NUM];
        for(int i=0;i<this.FLAVOR_MAX_NUM;i++){
            inputData.flavorList[i]=new Flavor();
            PredictFlavor[i]=new ClassPredictFlavor();
            Greedy[i]=new ClassGreedy();
        }
    }
    public void set(int FLAVOR_MAX_NUM){
        this.FLAVOR_MAX_NUM = FLAVOR_MAX_NUM;

    }
    DataTransform dataTransform;
    int FLAVOR_MAX_NUM ;
    int NotFound = -1;
    int PredictedCPU,PredictedMEM;
    static int CPUFIRST=0;                 
    static int MEMORYFIRST=1;                
    int[][] Priority;           
    int FLAVORINDEX=0;
    int OFFSET=1;

    Output output;//需要在类外new
    InputData inputData;//需要在类外new
    ClassGreedy[] Greedy;
    ClassPredictFlavor[] PredictFlavor;
    class InputData{
        int maxCpu;
        int maxMem;
        int flavorNum;
        int priorityResource;
        Flavor[] flavorList;
        void set(int maxCpu,int maxMem,int flavorNum,int length){
            this.maxCpu=maxCpu;
            this.maxMem=maxMem;
            this.flavorNum=flavorNum;
            flavorList=new Flavor[length];
        }
    }

    class Flavor{
        String flavorName;
        int cpuNum;
        int memoryNum;
        int flavorNO;
        public Flavor(){
            flavorName="";
            cpuNum=0;
            memoryNum=0;
        }
        void set(String flavorName,int cpuNum,int memoryNum){
            this.flavorName=flavorName;
            this.cpuNum=cpuNum;
            this.memoryNum=memoryNum;
        }
    }
    class ClassPredictFlavor {
        Flavor flavor;
        int PredictNum;
        public ClassPredictFlavor(){
            flavor=new Flavor();
        }
        void set(String flavorName,int cpuNum,int memoryNum,int PredictNum){
            flavor.set(flavorName,cpuNum,memoryNum);
            this.PredictNum=PredictNum;
        }
    }
    class ResultFlavor{
        Flavor flavor;
        String flavorName;
        int flavorNum;
        public ResultFlavor(){
            flavorNum=0;
            flavorName="";
            flavor=new Flavor();
        }
        void set(String flavorName,int flavorNum){
            this.flavorName=flavorName;
            this.flavorNum=flavorNum;
        }
    }
    class ResultServer{
        ResultFlavor[] flavorListInServer;
        int sumOfFlavorInServer;
        int id;
        int remainCPU;                                       
        int remainMEM;                                       
        public ResultServer(){
            flavorListInServer=new ResultFlavor[1000];
            for(int i=0;i<flavorListInServer.length;i++){
                flavorListInServer[i]=new ResultFlavor();
            }
        }
        void set(int length,int sumOfFlavorInServer,int id){
            flavorListInServer=new ResultFlavor[length];
            this.sumOfFlavorInServer=sumOfFlavorInServer;
            this.id=id;
        }
    }
    class Output{
        ResultFlavor[] resultFlavorList;
        int sumOfFlavor;
        int sumOfServers;
        ResultServer[] resultServersList;
        public Output(){
            resultFlavorList=new ResultFlavor[FLAVOR_MAX_NUM];
            resultServersList=new ResultServer[1000];
            for(int i=0;i<FLAVOR_MAX_NUM;i++){
                resultFlavorList[i]=new ResultFlavor();
                resultServersList[i]=new ResultServer();
            }
        }
        void set(int f_length,int sumOfFlavor,int sumOfServers,int s_length){
            resultFlavorList=new ResultFlavor[f_length];
            this.sumOfFlavor=sumOfFlavor;
            this.sumOfServers=sumOfServers;
            resultServersList=new ResultServer[s_length];
        }
    }
    class ClassGreedy
    {
        int flavorIndex;
        int flavorNum;
        double flavorPriority;
        void set(int flavorIndex,int flavorNum,double flavorPriority){
            this.flavorIndex=flavorIndex;
            this.flavorNum=flavorNum;
            this.flavorPriority=flavorPriority;
        }
    };

    void AllocationFunc(ClassPredictFlavor PredictFlavor[])
    {
        output.sumOfFlavor = 0;
        for(int i = 0; i < inputData.flavorNum; i++)
        {
            output.resultFlavorList[i].flavor.flavorName = PredictFlavor[i].flavor.flavorName;
            output.resultFlavorList[i].flavor.flavorNO = PredictFlavor[i].flavor.flavorNO;
            output.resultFlavorList[i].flavor.cpuNum = PredictFlavor[i].flavor.cpuNum;
            output.resultFlavorList[i].flavor.memoryNum = PredictFlavor[i].flavor.memoryNum;
            output.resultFlavorList[i].flavorNum = PredictFlavor[i].PredictNum;
            output.sumOfFlavor += PredictFlavor[i].PredictNum;
        }

        AllocationGreedySubFunc(PredictFlavor);

        if(inputData.priorityResource == CPUFIRST)
        {
            AdjustCPU();
        }
        else
        {
            AdjustMEM();
        }
        AdjustLast();
    }
    void AllocationGreedySubFunc(ClassPredictFlavor PredictFlavor[])
    {
        int releaseIndex;   

        int sumOfFlavor = InitGreedy(PredictFlavor);

        int numCPU = inputData.maxCpu;
        int numMem = inputData.maxMem;
        double targetPriority = (double)(numCPU)/(double)(numMem);
        int serversForCPU = dataTransform.Double2Int(Math.ceil((double)PredictedCPU/(double)numCPU));
        int serversForMEM = dataTransform.Double2Int(Math.ceil((double)PredictedMEM/(double)numMem));
        output.sumOfServers = 0;

        while(sumOfFlavor > 0)
        {
            numCPU = inputData.maxCpu;
            numMem = inputData.maxMem;
            int index;
            if(serversForCPU < serversForMEM)
            {
                index = GetMEMFirstIndex(numCPU, numMem);
            }
            else
            {
                index = GetCPUFirstIndex(numCPU, numMem);
            }
            if(index == NotFound)
                break;

            releaseIndex = index;

            output.sumOfServers ++;
            output.resultServersList[output.sumOfServers -1].id = output.sumOfServers;
            output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer = 0;

            int flavorIndex = Greedy[index].flavorIndex;
            int tempIndex = output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer;
            output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.flavorName = PredictFlavor[flavorIndex].flavor.flavorName;
            output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.flavorNO = PredictFlavor[flavorIndex].flavor.flavorNO;
            output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.cpuNum = PredictFlavor[flavorIndex].flavor.cpuNum;
            output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.memoryNum = PredictFlavor[flavorIndex].flavor.memoryNum;
            output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavorNum = 1;
            output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer++;

            Greedy[index].flavorNum --;
            sumOfFlavor --;
            numCPU -=  PredictFlavor[flavorIndex].flavor.cpuNum;
            numMem -=  PredictFlavor[flavorIndex].flavor.memoryNum;

            while(numCPU > 0 && numMem > 0)
            {
                double currentPriority = (double)(numCPU) / (double)(numMem);
                if(currentPriority >= targetPriority)
                {
                    index = GetCPUFirstIndex(numCPU, numMem);
                }
                else
                {
                    index = GetMEMFirstIndex(numCPU, numMem);
                }
                if(index == NotFound)
                {
                    break;
                }

                flavorIndex = Greedy[index].flavorIndex;
                int tempFlavorIndex = Greedy[releaseIndex].flavorIndex;

                if(inputData.priorityResource == CPUFIRST)
                {
                    if(PredictFlavor[flavorIndex].flavor.memoryNum > PredictFlavor[tempFlavorIndex].flavor.memoryNum)
                        releaseIndex = index;
                }
                else
                {
                    if(PredictFlavor[flavorIndex].flavor.cpuNum > PredictFlavor[tempFlavorIndex].flavor.cpuNum)
                        releaseIndex = index;
                }

                tempIndex = output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer;
                int indexOfFlavor = GetFlavorInServer(output.sumOfServers - 1, tempIndex, PredictFlavor[flavorIndex].flavor.flavorName);

                if(indexOfFlavor != NotFound)
                {
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[indexOfFlavor].flavorNum ++;
                }
                else
                {
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.flavorName = PredictFlavor[flavorIndex].flavor.flavorName;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.flavorNO = PredictFlavor[flavorIndex].flavor.flavorNO;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.cpuNum = PredictFlavor[flavorIndex].flavor.cpuNum;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.memoryNum = PredictFlavor[flavorIndex].flavor.memoryNum;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavorNum = 1;
                    output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer++;
                }

                Greedy[index].flavorNum --;
                sumOfFlavor --;
                numCPU -=  PredictFlavor[flavorIndex].flavor.cpuNum;
                numMem -=  PredictFlavor[flavorIndex].flavor.memoryNum;
            }

            output.resultServersList[output.sumOfServers -1].remainCPU = numCPU;
            output.resultServersList[output.sumOfServers -1].remainMEM = numMem;

            if(inputData.priorityResource == CPUFIRST && numCPU == 0)
                continue;
            if(inputData.priorityResource == MEMORYFIRST && numMem == 0)
                continue;

            int releaseflavorIndex = Greedy[releaseIndex].flavorIndex;
            tempIndex = output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer;
            int indexOfFlavor = GetFlavorInServer(output.sumOfServers - 1, tempIndex, PredictFlavor[releaseflavorIndex].flavor.flavorName);
            if(indexOfFlavor == NotFound)
                continue;

            output.resultServersList[output.sumOfServers -1].flavorListInServer[indexOfFlavor].flavorNum--;
            sumOfFlavor++;
            Greedy[releaseIndex].flavorNum ++;
            numCPU += PredictFlavor[releaseflavorIndex].flavor.cpuNum;
            numMem += PredictFlavor[releaseflavorIndex].flavor.memoryNum;

            output.resultServersList[output.sumOfServers -1].remainCPU = numCPU;
            output.resultServersList[output.sumOfServers -1].remainMEM = numMem;

            while(numCPU > 0 && numMem > 0)
            {
                if(inputData.priorityResource == CPUFIRST)
                {
                    index = GetCPUFirstIndex(numCPU, numMem);
                }
                else
                {
                    index = GetMEMFirstIndex(numCPU, numMem);
                }
                if(index == NotFound)
                {
                    break;
                }
                flavorIndex = Greedy[index].flavorIndex;
                tempIndex = output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer;
                indexOfFlavor = GetFlavorInServer(output.sumOfServers - 1, tempIndex, PredictFlavor[flavorIndex].flavor.flavorName);

                if(indexOfFlavor != NotFound)
                {
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[indexOfFlavor].flavorNum ++;
                }
                else
                {
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.flavorName = PredictFlavor[flavorIndex].flavor.flavorName;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.flavorNO = PredictFlavor[flavorIndex].flavor.flavorNO;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.cpuNum = PredictFlavor[flavorIndex].flavor.cpuNum;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavor.memoryNum = PredictFlavor[flavorIndex].flavor.memoryNum;
                    output.resultServersList[output.sumOfServers -1].flavorListInServer[tempIndex].flavorNum = 1;
                    output.resultServersList[output.sumOfServers -1].sumOfFlavorInServer++;
                }

                Greedy[index].flavorNum --;
                sumOfFlavor --;
                numCPU -=  PredictFlavor[flavorIndex].flavor.cpuNum;
                numMem -=  PredictFlavor[flavorIndex].flavor.memoryNum;
            }

            output.resultServersList[output.sumOfServers -1].remainCPU = numCPU;
            output.resultServersList[output.sumOfServers -1].remainMEM = numMem;
        }
    }

    int InitGreedy(ClassPredictFlavor PredictFlavor[])
    {

        int sumOfFlavor = 0;
        PredictedCPU = 0;
        PredictedMEM = 0;
        double targetPriority = (double)(inputData.maxCpu)/(double)(inputData.maxMem);
        for(int i = 0; i < inputData.flavorNum; i++)
        {
            sumOfFlavor += PredictFlavor[i].PredictNum;
            Greedy[i].flavorPriority = inputData.flavorList[i].cpuNum - targetPriority * inputData.flavorList[i].memoryNum;
            Greedy[i].flavorIndex = i;
            Greedy[i].flavorNum = PredictFlavor[i].PredictNum;

            PredictedCPU += PredictFlavor[i].flavor.cpuNum * PredictFlavor[i].PredictNum;
            PredictedMEM += PredictFlavor[i].flavor.memoryNum * PredictFlavor[i].PredictNum;
            Priority[i][FLAVORINDEX] = i;
            Priority[i][OFFSET] = inputData.flavorList[i].cpuNum - inputData.flavorList[i].memoryNum;
        }


        for(int i = 0; i < inputData.flavorNum; i++)
        {
            for(int j = i + 1; j < inputData.flavorNum; j ++)
            {
                if(Greedy[i].flavorPriority < Greedy[j].flavorPriority)
                {
                    int tempFlavorIndex = Greedy[i].flavorIndex;
                    int tempFlavorNum = Greedy[i].flavorNum;
                    double tempPriority = Greedy[i].flavorPriority;
                    Greedy[i].flavorIndex = Greedy[j].flavorIndex;
                    Greedy[i].flavorNum = Greedy[j].flavorNum;
                    Greedy[i].flavorPriority = Greedy[j].flavorPriority;
                    Greedy[j].flavorIndex = tempFlavorIndex;
                    Greedy[j].flavorNum = tempFlavorNum;
                    Greedy[j].flavorPriority = tempPriority;
                }

                if(Priority[i][OFFSET] <= Priority[j][OFFSET])
                {
                    int tempIndex = Priority[i][FLAVORINDEX];
                    int tempOffset = Priority[i][OFFSET];
                    Priority[i][FLAVORINDEX] = Priority[j][FLAVORINDEX];
                    Priority[i][OFFSET] = Priority[j][OFFSET];
                    Priority[j][FLAVORINDEX] = tempIndex;
                    Priority[j][OFFSET] = tempOffset;
                }
            }
        }
        return sumOfFlavor;
    }

    int GetCPUFirstIndex(int idleCPU, int idleMEM)
    {
        for(int i = 0; i < inputData.flavorNum; i ++)
        {
            if(Greedy[i].flavorNum > 0 && idleCPU >= inputData.flavorList[Greedy[i].flavorIndex].cpuNum
                    && idleMEM >= inputData.flavorList[Greedy[i].flavorIndex].memoryNum)
                return i;
        }
        return NotFound;
    }


    int GetMEMFirstIndex(int idleCPU, int idleMEM)
    {
        for(int i = inputData.flavorNum - 1; i >= 0 ; i --)
        {
            if(Greedy[i].flavorNum > 0 && idleCPU >= inputData.flavorList[Greedy[i].flavorIndex].cpuNum
                    && idleMEM >= inputData.flavorList[Greedy[i].flavorIndex].memoryNum)
                return i;
        }
        return NotFound;
    }

    int GetFlavorInServer(int indexOfServers, int numFlavor, String name)
    {
        for(int i = 0; i < numFlavor; i++)
        {
            if(name.equals(output.resultServersList[indexOfServers].flavorListInServer[i].flavor.flavorName))
                return i;
        }
        return NotFound;
    }

    void AdjustCPU()
    {
        for(int i = 0; i < output.sumOfServers - 1; i++)
        {
            if(output.resultServersList[i].remainCPU == 0)
                continue;
            FillSeverWithCPUIndex(i);
        }
    }

    void FillSeverWithCPUIndex(int index)
    {
        while (output.resultServersList[index].remainCPU > 0 && output.resultServersList[index].remainMEM > 0)
        {
            int remainMem = output.resultServersList[index].remainMEM;
            int remainCpu = output.resultServersList[index].remainCPU;
            boolean isFilled = false;

            for(int i = 0; i < inputData.flavorNum; i ++)
            {
                int flavorIndex = Priority[i][FLAVORINDEX];
                if(inputData.flavorList[flavorIndex].cpuNum <= remainCpu && inputData.flavorList[flavorIndex].memoryNum <= remainMem)
                {
                    int tempIndex = output.resultServersList[index].sumOfFlavorInServer;
                    int indexOfFlavor = GetFlavorInServer(index , tempIndex, inputData.flavorList[flavorIndex].flavorName);
                    if(indexOfFlavor != NotFound)
                    {
                        output.resultServersList[index].flavorListInServer[indexOfFlavor].flavorNum ++;
                    }
                    else
                    {
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.flavorName = inputData.flavorList[flavorIndex].flavorName;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.flavorNO = inputData.flavorList[flavorIndex].flavorNO;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.cpuNum = inputData.flavorList[flavorIndex].cpuNum;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.memoryNum = inputData.flavorList[flavorIndex].memoryNum;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavorNum = 1;
                        output.resultServersList[index].sumOfFlavorInServer++;
                    }
                    output.resultFlavorList[flavorIndex].flavorNum++;
                    output.sumOfFlavor++;
                    isFilled = true;
                    output.resultServersList[index].remainCPU -= inputData.flavorList[flavorIndex].cpuNum;
                    output.resultServersList[index].remainMEM -= inputData.flavorList[flavorIndex].memoryNum;
                    break;
                }
            }

            if(isFilled == false)
                break;
        }
    }


    void AdjustMEM()
    {
        for(int i = 0; i < output.sumOfServers - 1; i++)
        {
            if(output.resultServersList[i].remainMEM == 0)
                continue;
            FillSeverWithMEM(i);
        }
    }

    void FillSeverWithMEM(int index)
    {
        while (output.resultServersList[index].remainMEM > 0 && output.resultServersList[index].remainCPU > 0)
        {
            int remainMem = output.resultServersList[index].remainMEM;
            int remainCpu = output.resultServersList[index].remainCPU;
            boolean isFilled = false;

            for(int i = inputData.flavorNum - 1; i >=0 ; i--)
            {
                int flavorIndex = Priority[i][FLAVORINDEX];
                if(inputData.flavorList[flavorIndex].cpuNum <= remainCpu && inputData.flavorList[flavorIndex].memoryNum <= remainMem)
                {
                    int tempIndex = output.resultServersList[index].sumOfFlavorInServer;
                    int indexOfFlavor = GetFlavorInServer(index , tempIndex, inputData.flavorList[flavorIndex].flavorName);
                    if(indexOfFlavor != NotFound)
                    {
                        output.resultServersList[index].flavorListInServer[indexOfFlavor].flavorNum ++;
                    }
                    else
                    {
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.flavorName = inputData.flavorList[flavorIndex].flavorName;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.flavorNO = inputData.flavorList[flavorIndex].flavorNO;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.cpuNum = inputData.flavorList[flavorIndex].cpuNum;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavor.memoryNum = inputData.flavorList[flavorIndex].memoryNum;
                        output.resultServersList[index].flavorListInServer[tempIndex].flavorNum = 1;
                        output.resultServersList[index].sumOfFlavorInServer++;
                    }
                    output.resultFlavorList[flavorIndex].flavorNum++;
                    output.sumOfFlavor++;
                    isFilled = true;
                    output.resultServersList[index].remainCPU -= inputData.flavorList[flavorIndex].cpuNum;
                    output.resultServersList[index].remainMEM -= inputData.flavorList[flavorIndex].memoryNum;
                    break;
                }
            }

            if(isFilled == false)
                break;
        }
    }


    void AdjustLast()
    {
        double deleteLine = 0.9;
        double fillLine = 0.7;
        double resourceRemainRate;
        int indexOfServer = output.sumOfServers - 1;
        int numOfFlaver = output.resultServersList[indexOfServer].sumOfFlavorInServer;
        int surPlusCpu = output.resultServersList[indexOfServer].remainCPU;
        int surPlusMem = output.resultServersList[indexOfServer].remainMEM;

        if(inputData.priorityResource == CPUFIRST)
        {
            resourceRemainRate = (double)(surPlusCpu)/(double)(inputData.maxCpu);

            if(resourceRemainRate > deleteLine)
            {
                for(int i = 0; i < numOfFlaver; i ++)
                {
                    int tempIndex =	IsEffectFlavor(output.resultServersList[indexOfServer].flavorListInServer[i].flavor.flavorNO);
                    output.resultFlavorList[tempIndex].flavorNum -= output.resultServersList[indexOfServer].flavorListInServer[i].flavorNum;
                    output.sumOfFlavor -= output.resultServersList[indexOfServer].flavorListInServer[i].flavorNum;
                }
                output.sumOfServers --;
                return;
            }

            if(resourceRemainRate < fillLine)
            {
                FillSeverWithCPUIndex(indexOfServer);
            }
        }
        else
        {
            resourceRemainRate = (double)(surPlusMem)/(double)(inputData.maxMem);

            if(resourceRemainRate > deleteLine)
            {
                for(int i = 0; i < numOfFlaver; i ++)
                {
                    int tempIndex =	IsEffectFlavor(output.resultServersList[indexOfServer].flavorListInServer[i].flavor.flavorNO);
                    output.resultFlavorList[tempIndex].flavorNum -= output.resultServersList[indexOfServer].flavorListInServer[i].flavorNum;
                    output.sumOfFlavor -= output.resultServersList[indexOfServer].flavorListInServer[i].flavorNum;
                }
                output.sumOfServers --;
                return;
            }

            if(resourceRemainRate < fillLine)
            {
                FillSeverWithMEM(indexOfServer);
            }
        }

    }
    int IsEffectFlavor(int tempFlavorNo)
    {
        for(int i=0;i<inputData.flavorNum;i++)
        {
            if(tempFlavorNo==inputData.flavorList[i].flavorNO)
                return i;
        }
        return NotFound;
    }
}
