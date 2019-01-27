package Controller;

import Model.Node;

import java.util.ArrayList;

class Sorting {
    private boolean lowToHigh, highToLow;

    Sorting() {
        lowToHigh = true;
        highToLow = false;
    }

    void bubbleSort(ArrayList<Node> list) {
        int Switch = -1;
        Node temp;

        while(Switch != 0) {
            Switch = 0;

            if(lowToHigh) {
                for(int i = 0; i < list.size() - 1; i++) {
                    if(list.get(i).getF() > list.get(i + 1).getF()) {
                        temp = list.get(i);
                        list.remove(i);
                        list.add(i + 1, temp);
                        Switch = 1;
                    }
                }
            } else if(highToLow) {
                for(int i = 0; i < list.size() - 1; i++) {
                    if(list.get(i).getF() < list.get(i).getF()) {
                        temp = list.get(i);
                        list.remove(i);
                        list.add(i + 1, temp);
                        Switch = 1;
                    }
                }
            }
        }
    }
}
