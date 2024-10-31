package dev.lone64.roseframework.spigot.builder.language;

import dev.lone64.roseframework.spigot.RoseModule;
import dev.lone64.roseframework.spigot.builder.config.yaml.YamlConfigBuilder;
import dev.lone64.roseframework.spigot.builder.language.data.Language;
import dev.lone64.roseframework.spigot.builder.language.storage.CachedLangList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class LanguageBuilder extends YamlConfigBuilder {

    private final String folderName;
    private final Map<Class<? extends Language>, Language> languageMap = new HashMap<>();

    public LanguageBuilder(RoseModule module, String folderName) {
        super(module, folderName, true);

        this.folderName = folderName;
    }

    public void reloadAll() {
        for (var entry : getLanguageMap().values()) {
            entry.reload();
        }
    }

    public void addLanguage(Language... languages) {
        for (var language : languages) {
            getLanguageMap().put(language.getClass(), CachedLangList.set(language.getName(), language));
        }
    }

    public void addLanguage(List<Language> languages) {
        addLanguage(languages.toArray(new Language[0]));
    }

    public Language getLanguage(String langId) {
        for (var language : getLanguageMap().values()) {
            if (language.getName().equalsIgnoreCase(langId)) {
                return language;
            }
        }
        return null;
    }

    public boolean isLanguage(String langId) {
        return getLanguage(langId) != null;
    }

    public List<String> getLanguageKeys() {
        return getLanguageMap().values().stream().map(Language::getName).toList();
    }

    public List<Language> getLanguages() {
        return new ArrayList<>(getLanguageMap().values());
    }

}