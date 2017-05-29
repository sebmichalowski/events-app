package pl.websm.model;

abstract class BaseModel {

    private Integer id;
    private String name;

    BaseModel(Integer id, String name) {
        setId(id);
        setName(name);
    }

    BaseModel(String name) {
        setName(name);
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}