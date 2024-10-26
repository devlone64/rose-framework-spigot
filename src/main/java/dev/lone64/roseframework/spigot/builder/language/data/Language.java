package dev.lone64.roseframework.spigot.builder.language.data;

import dev.lone64.roseframework.spigot.RoseModule;
import dev.lone64.roseframework.spigot.builder.config.yaml.YamlConfigBuilder;
import dev.lone64.roseframework.spigot.builder.language.LanguageBuilder;
import lombok.Getter;

@Getter
public class Language extends YamlConfigBuilder {

    private final boolean enabled;
    private final String name;

    public Language(LanguageBuilder lang, String name) {
        this(lang, true, name);
    }

    public Language(LanguageBuilder lang, boolean enabled, String name) {
        super(RoseModule.INSTANCE, lang.getFolderName(), "messages_%s.yml".formatted(name));

        this.enabled = enabled;
        this.name = name;
    }

}