package io.mygdx.soulknight.managers;

/**
 * Simple singleton holder so weapons can add bullets without passing a reference
 * around everywhere. Set it once in PlayScreen constructor:
 *   EntityManagerHolder.set(entityManager);
 */
public class EntityManagerHolder {
    private static EntityManager manager;
    public static void set(EntityManager m) { manager = m; }
    public static EntityManager get() { return manager; }
}
