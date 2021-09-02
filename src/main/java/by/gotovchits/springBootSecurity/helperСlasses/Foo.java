package by.gotovchits.springBootSecurity.helperСlasses;

import by.gotovchits.springBootSecurity.models.User;

import java.util.List;

public class Foo {
    private List<User> checkedItems;

    public List<User> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(List<User> checkedItems) {
        this.checkedItems = checkedItems;
    }
}