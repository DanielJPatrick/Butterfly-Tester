package dragonfly.ormtester.models;

import dragonfly.butterfly.DatabaseLink;

public class Specie {
    Integer id;
    String name;
    String iconName;

    @DatabaseLink(tableName = "Specie", columnName = "Id")
    public Integer getId() {
        return id;
    }

    @DatabaseLink(tableName = "Specie", columnName = "Id")
    public void setId(Integer id) {
        this.id = id;
    }

    @DatabaseLink(tableName = "Specie", columnName = "Name")
    public String getName() {
        return name;
    }

    @DatabaseLink(tableName = "Specie", columnName = "Name")
    public void setName(String name) {
        this.name = name;
    }

    @DatabaseLink(tableName = "Specie", columnName = "IconName")
    public String getIconName() {
        return iconName;
    }

    @DatabaseLink(tableName = "Specie", columnName = "IconName")
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
