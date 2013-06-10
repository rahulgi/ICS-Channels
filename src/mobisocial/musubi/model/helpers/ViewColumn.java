package mobisocial.musubi.model.helpers;

public class ViewColumn {
    private final String tableColumn;
    private final String viewColumn;
    private final String tableName;

    public ViewColumn(String commonColumnName, String tableName) {
        this.viewColumn = commonColumnName;
        this.tableColumn = commonColumnName;
        this.tableName = tableName;
    }

    public ViewColumn(String viewColumn, String tableName, String tableColumn) {
        this.viewColumn = viewColumn;
        this.tableColumn = tableColumn;
        this.tableName = tableName;
    }

    /**
     * eg my_table.its_col
     */
    public String getTableColumn() {
        return tableName + "." + tableColumn;
    }

    /**
     * eg my_view_col
     */
    public String getViewColumn() {
        return viewColumn;
    }
}