package io.mygdx.soulknight.Tools;

public interface Contactable {
    void onContact(Contactable object);
    void offContact(Contactable object);

    Info getInfo();
}
