package highScoreKit.fullExploration;

public class PlacingIntegersInASpiral {
    private int x, y, nextValue;
    private int[][] spiral;

    public int[][] solution(int n) {
        x = 0; y = -1; nextValue = 1;
        spiral = new int[n][n];
        fillInSpiral('R');

        return spiral;
    }

    private void fillInSpiral(char direction) {
        if (nextValue > spiral[0].length * spiral.length) return;

        switch (direction) {
            case 'R': {
                fillInRight();
                fillInSpiral('D');
                break;
            }
            case 'D': {
                fillInDown();
                fillInSpiral('L');
                break;
            }
            case 'L': {
                fillInLeft();
                fillInSpiral('U');
                break;
            }
            case 'U': {
                fillInUp();
                fillInSpiral('R');
                break;
            }
        }
    }

    private void fillInRight() {
        for (int i = y + 1; i < spiral[x].length; i++) {
            if (spiral[x][i] == 0) {
                spiral[x][i] = nextValue++;
                y = i;
            } else break;
        }
    }

    private void fillInLeft() {
        for (int i = y - 1; i >= 0; i--) {
            if (spiral[x][i] == 0) {
                spiral[x][i] = nextValue++;
                y = i;
            } else break;
        }
    }

    private void fillInDown() {
        for (int i = x + 1; i < spiral.length; i++) {
            if (spiral[i][y] == 0) {
                spiral[i][y] = nextValue++;
                x = i;
            } else break;
        }
    }

    private void fillInUp() {
        for (int i = x - 1; i >= 0; i--) {
            if (spiral[i][y] == 0) {
                spiral[i][y] = nextValue++;
                x = i;
            } else break;
        }
    }
}
