package ImagePackage;

class Pixel {
    private int blue;
    private int row;
    private int column;

    public Pixel(int blue, int row, int column){
        this.blue = blue;
        this.row = row;
        this.column = column;
    }

    public int getBlue() {
        return blue;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
