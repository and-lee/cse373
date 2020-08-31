import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

public class setCover {
    public static void main(String[] args) throws IOException{
        BufferedReader bReader = null;
        long startTime = 0;
        long endTime = 0;

        try {
            String fileName = "s-k-30-50";
            FileReader file = new FileReader("./" + fileName + ".txt");
            System.out.println("File: " + fileName);
            bReader = new BufferedReader(file);

            int size = Integer.parseInt(bReader.readLine());
            int numSets = Integer.parseInt(bReader.readLine());
            universalSet set = new universalSet(size, numSets);
            
            String currLine;
            while((currLine = bReader.readLine()) != null) {
                set.addSet(currLine);
            }
            startTime = System.nanoTime();
            set.findMinSetCover();
            endTime = System.nanoTime();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Run Time: " + ((endTime-startTime)/1000000000.0) + "s");
            bReader.close();
        }
        System.out.println();
        try {
            String fileName = "s-rg-63-25";
            FileReader file = new FileReader("./" + fileName + ".txt");
            System.out.println("File: " + fileName);
            bReader = new BufferedReader(file);

            int size = Integer.parseInt(bReader.readLine());
            int numSets = Integer.parseInt(bReader.readLine());
            universalSet set = new universalSet(size, numSets);
            
            String currLine;
            while((currLine = bReader.readLine()) != null) {
                set.addSet(currLine);
            }
            startTime = System.nanoTime();
            set.findMinSetCover();
            endTime = System.nanoTime();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Run Time: " + ((endTime-startTime)/1000000000.0) + "s");
            bReader.close();
        }

    }
}

class universalSet {
int size;
int numSets;
List<Set<Integer>> data;

int minCover;
List<Set<Integer>> sol;
int[] counter;
Set<Integer> cover;

    public universalSet(int size, int numSets){
        this.size = size;
        this.numSets = numSets;
        data = new ArrayList<>();

        minCover = this.size;
        sol = new ArrayList<>();

        counter = new int[this.size];
        cover = new HashSet<>();
    }

    public void findMinSetCover() {
        List<Set<Integer>> solution = new ArrayList<>();
        backtrack(solution);
        System.out.println("Minimum set cover is: " + minCover);
        System.out.println(sol);
    }

    public void backtrack(List<Set<Integer>> solution) {
        if (solution.size() >= minCover) { // pruning: minsetCover number > current minSetCover number then stop
            return;
        }
        List<Set<Integer>> candidates = new ArrayList<>();
        
        //Set<Integer> cover = new HashSet<>(); // reset
        cover = new HashSet<>();
        for (int i=0; i<solution.size(); i++) {
            cover.addAll(solution.get(i));
        }
        
        if(is_a_solution(cover)) {
            process_solution(solution);
        } else {
            // CONSTRUCT CANDIDATES
            if (solution.isEmpty()) {

                // pruning - add sets that are definitely in the solution (set that has only instance of point)
                Set<Integer> unique = new HashSet<>();
                for (int i=0; i<counter.length; i++) {
                    if(counter[i]==1) { // definitely in the solution
                        unique.add(i+1);
                    }
                }
                for (int j = 0; j<data.size(); j++) {
                    if(unique.removeAll(data.get(j))) {
                        solution.add(data.get(j)); // add set to solution (is in solution)
                        data.remove(j); // remove from data/candidates
                    }
                }

                // pruning
                Collections.sort(data, setUniqueCoverComparator); // sort in order of most unvisited
                candidates = data;
                
            } else {
                int total = 0;
                for(int i=data.indexOf(solution.get(solution.size() - 1)); i<data.size(); i++) { // combinations - no repeats
                    total = total + data.get(i).size();
                    
                    if (!cover.containsAll(data.get(i))) { // pruning - if already covered in the partial solution, do not add set
                        candidates.add(data.get(i));
                    }
                }
                if (total<(size-cover.size())) { // pruning end when we know cover is not possible
                    return;
                }
                // pruning - add next candidate with most unvisited
                //Collections.sort(candidates, setUniqueCoverComparator);
            }

            // backtrack
            for(int i=0; i<candidates.size(); i++) {
                solution.add(candidates.get(i)); // make move
                backtrack(solution);
                solution.remove(solution.size()-1); // unmake move
            }
        }
    }

    public void addSet(String line) {
        if (line.trim().equals("")) { // pruning - don't add empty sets
            return;
        }
        String[] nums = line.split(" ");
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int num = Integer.parseInt(nums[i]);
            counter[num-1]++;
            set.add(num);
        }
        data.add(set);
    }

    public boolean is_a_solution(Set<Integer> cover) {
        return cover.size() == size;
    }

    public void process_solution(List<Set<Integer>> solution) {
        int cover = solution.size();
        if (minCover > cover) {
            minCover = cover;
            sol = new ArrayList(solution);
        }
    }

    Comparator<Set<Integer>> setUniqueCoverComparator = new Comparator<Set<Integer>>() {
        // sort by most unvisited = cover.clone().removeAll(candidates.get(i)).size();
        @Override
        public int compare(Set o1, Set o2)
        {
            //return o2.size() - o1.size();
            Set<Integer> cover1 = new HashSet<>(cover);
            cover1.removeAll(o1);
            Set<Integer> cover2 = new HashSet<>(cover);
            cover2.removeAll(o2);
            
            return cover1.size() - cover2.size();
        }
    };        

}