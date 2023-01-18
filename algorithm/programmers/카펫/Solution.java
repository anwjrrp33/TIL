package algorithm.programmers.카펫;

class Solution {
    public int[] solution(int brown, int yellow) {
        Carpet carpet = new Carpet(brown + yellow, 0, brown + yellow);
        
        while (carpet.row >= carpet.column) {
            carpet.cut();

            if (carpet.sum % carpet.row != 0) {
                continue;
            }

            if (carpet.isComplete(yellow)) {
                break;
            }
        }

        return carpet.size();
    }

    class Carpet {
        int row;

        int column;

        int sum;

        public Carpet(int row, int column, int sum) {
            this.row = row;
            this.column = column;
            this.sum = sum;
        }

        public void cut() {
            this.row--;
            this.column = sum / row;
        }

        public boolean isComplete(int yellow) {
            return yellow == (this.row - 2) * (this.column - 2);
        }

        public int[] size() {
            return new int[] { this.row, this.column };
        }
    }
}