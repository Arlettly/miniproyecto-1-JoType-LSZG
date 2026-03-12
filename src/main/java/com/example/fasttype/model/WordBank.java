package com.example.fasttype.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the word pool for the Fast Typing game, organized by difficulty tiers.
 *
 * Words are divided into five difficulty tiers aligned with the game's 45 levels.
 *
 * @author Lesly Zapata
 * @version 1.1
 */
public class WordBank {

    // -------------------------------------------------------------------------
    // Tier Boundaries
    // -------------------------------------------------------------------------

    public static final int TIER1_MAX = 9;
    public static final int TIER2_MAX = 18;
    public static final int TIER3_MAX = 27;
    public static final int TIER4_MAX = 36;

    // -------------------------------------------------------------------------
    // Word Pools per Tier
    // -------------------------------------------------------------------------

    /** Tier 1 – Easy */
    private final List<String> tier1 = new ArrayList<>(List.of(
            "web","cpu","ram","bit","api",
            "log","css","dom","bug","sql",
            "dns","ide","app","url","gui"
    ));

    /** Tier 2 – Medium */
    private final List<String> tier2 = new ArrayList<>(List.of(
            "Script","Código","Server","Cliente","Docker",
            "GitHub","Python","Kernel","Router","Parser",
            "Socket","Thread","Buffer","Token","Switch",
            "Linus","Torvalds"
    ));

    /** Tier 3 – Hard */
    private final List<String> tier3 = new ArrayList<>(List.of(
            "ALGORITMO","VARIABLE","FUNCIONES","INTERFAZ","PROCESADO",
            "REPOSITOR","COMPILADO","SERVIDOR","ITERACION","DEPURADOR",
            "CONEXION","PAQUETES","LIBRERIAS","MODULARES","PROTOCOLO"
    ));

    /** Tier 4 – Very Hard */
    private final List<String> tier4 = new ArrayList<>(List.of(
            "programación","optimización","sincronización","compilación","depuración",
            "virtualización","comunicación","documentación","implementación","configuración",
            "inicialización","actualización","autenticación","administración","organización"
    ));

    /** Tier 5 – Extreme */
    private final List<String> tier5 = new ArrayList<>(List.of(
            "microarquitectura","deserialización","hiperconectividad","internacionalización",
            "paralelización","interoperabilidad","desfragmentación","transcompilación",
            "metaprogramación","autoescalabilidad","virtualización","descompilación",
            "serialización","modularización","optimizabilidad"
    ));

    /** Shuffled working copy of the currently active tier */
    private final List<String> currentPool = new ArrayList<>();

    /** Index into the current shuffled pool */
    private int poolIndex;

    /** The tier currently loaded */
    private int loadedTier;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public WordBank() {
        loadedTier = -1;
    }

    // -------------------------------------------------------------------------
    // Public Methods
    // -------------------------------------------------------------------------

    /**
     * Returns a word appropriate for the given game level.
     */
    public String getWordForLevel(int level) {

        int tier = getTierForLevel(level);

        if (tier != loadedTier || poolIndex >= currentPool.size()) {
            loadPool(tier);
        }

        return currentPool.get(poolIndex++);
    }

    // -------------------------------------------------------------------------
    // Private Helpers
    // -------------------------------------------------------------------------

    private int getTierForLevel(int level) {

        if (level <= TIER1_MAX) return 1;
        if (level <= TIER2_MAX) return 2;
        if (level <= TIER3_MAX) return 3;
        if (level <= TIER4_MAX) return 4;

        return 5;
    }

    /**
     * Loads and shuffles the word list for the given tier
     */
    private void loadPool(int tier) {

        currentPool.clear();

        switch (tier) {
            case 1 -> currentPool.addAll(tier1);
            case 2 -> currentPool.addAll(tier2);
            case 3 -> currentPool.addAll(tier3);
            case 4 -> currentPool.addAll(tier4);
            default -> currentPool.addAll(tier5);
        }

        Collections.shuffle(currentPool);

        poolIndex = 0;
        loadedTier = tier;
    }
}