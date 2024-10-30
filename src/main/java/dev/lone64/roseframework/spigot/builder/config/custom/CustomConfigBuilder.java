package dev.lone64.roseframework.spigot.builder.config.custom;

import dev.lone64.roseframework.spigot.builder.config.ConfigBuilderProvider;
import dev.lone64.roseframework.spigot.builder.config.custom.annotation.DeleteIfIncomplete;
import dev.lone64.roseframework.spigot.builder.config.custom.annotation.DeleteOnEmpty;
import dev.lone64.roseframework.spigot.builder.config.custom.processor.DeleteIfIncompleteProcessor;
import dev.lone64.roseframework.spigot.builder.config.custom.processor.DeleteOnEmptyProcessor;
import dev.lone64.roseframework.spigot.builder.config.custom.serializer.BigDecimalTypeSerializer;
import dev.lone64.roseframework.spigot.builder.config.custom.serializer.ItemStackTypeSerializer;
import dev.lone64.roseframework.spigot.builder.config.custom.serializer.LocationTypeSerializer;
import dev.lone64.roseframework.spigot.util.file.FileUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class CustomConfigBuilder implements ConfigBuilderProvider {

    private static ObjectMapper.Factory MAPPER_FACTORY = ObjectMapper.factoryBuilder()
            .addProcessor(DeleteOnEmpty.class, (data, value) -> new DeleteOnEmptyProcessor())
            .addProcessor(DeleteIfIncomplete.class, (data, value) -> new DeleteIfIncompleteProcessor())
            .build();
    private static TypeSerializerCollection serializers = TypeSerializerCollection.defaults().childBuilder()
            .registerAnnotatedObjects(MAPPER_FACTORY)
            .register(Location.class, new LocationTypeSerializer())
            .register(BigDecimal.class, new BigDecimalTypeSerializer())
            .register(ItemStack.class, new ItemStackTypeSerializer())
            .build();

    private CommentedConfigurationNode configurationNode;

    private File file;
    private JavaPlugin plugin;
    private YamlConfigurationLoader loader;

    public CustomConfigBuilder(JavaPlugin plugin, String name) {
        this(plugin, name, null, true);
    }

    public CustomConfigBuilder(JavaPlugin plugin, String name, boolean load) {
        this(plugin, name, null, load);
    }

    public CustomConfigBuilder(JavaPlugin plugin, String name, String header) {
        this(plugin, name, header, true);
    }

    public CustomConfigBuilder(JavaPlugin plugin, String name, String header, boolean load) {
        this(new File(plugin.getDataFolder(), name), plugin, header, load);
    }

    public CustomConfigBuilder(File file, JavaPlugin plugin) {
        this(file, plugin, null, true);
    }

    public CustomConfigBuilder(File file, JavaPlugin plugin, String header) {
        this(file, plugin, header, true);
    }

    public CustomConfigBuilder(File file, JavaPlugin plugin, String header, boolean load) {
        this.file = file;
        this.plugin = plugin;
        this.loader = YamlConfigurationLoader.builder().defaultOptions(opts -> opts.header(header).serializers(serializers))
                .headerMode(HeaderMode.PRESET).nodeStyle(NodeStyle.BLOCK).indent(2).file(file).build();
        if (load) load();
    }

    public boolean create() {
        if (FileUtil.isDirectory(getFile().getName()))
            return FileUtil.createFolder(getFile().getPath());
        return FileUtil.createFile(getFile().getPath());
    }

    public boolean remove() {
        if (FileUtil.isDirectory(getFile().getName()))
            return FileUtil.deleteFolder(getFile().getPath());
        return FileUtil.deleteFile(getFile().getPath());
    }

    public void reload() {
        load();
    }

    public CustomConfigBuilder add(String path, Object value) {
        if (!is(path)) set(path, value);
        return this;
    }

    public CustomConfigBuilder set(String path, Object value) {
        setInternal(path, value);
        return this;
    }

    public CustomConfigBuilder setList(String path, Class<?> type, Object value) {
        setInternal(path, type, value);
        return this;
    }

    public CustomConfigBuilder remove(String path) {
        setInternal(path, null);
        return this;
    }

    public Object get(String path) {
        CommentedConfigurationNode node = getInternal(path);
        return node.raw();
    }

    public Object get(String path, Object def) {
        CommentedConfigurationNode node = getInternal(path);
        if (node == null) return def;
        return node.raw();
    }

    public <T> T get(String path, Class<T> type) {
        CommentedConfigurationNode node = getInternal(path);
        try {return node.get(type);} catch (SerializationException e) {return null;}
    }

    public String getString(String path) {
        CommentedConfigurationNode node = getInternal(path);
        return node.getString();
    }

    public String getString(String path, String def) {
        CommentedConfigurationNode node = getInternal(path);
        if (node == null) return def;
        return node.getString();
    }

    public int getInt(String path) {
        CommentedConfigurationNode node = getInternal(path);
        return node.getInt();
    }

    public int getInt(String path, int def) {
        CommentedConfigurationNode node = getInternal(path);
        if (node == null) return def;
        return node.getInt();
    }

    public double getDouble(String path) {
        CommentedConfigurationNode node = getInternal(path);
        return node.getDouble();
    }

    public double getDouble(String path, double def) {
        CommentedConfigurationNode node = getInternal(path);
        if (node == null) return def;
        return node.getDouble();
    }

    public float getFloat(String path) {
        CommentedConfigurationNode node = getInternal(path);
        return node.getFloat();
    }

    public float getFloat(String path, float def) {
        CommentedConfigurationNode node = getInternal(path);
        if (node == null) return def;
        return node.getFloat();
    }

    public boolean getBoolean(String path) {
        CommentedConfigurationNode node = getInternal(path);
        return node.getBoolean();
    }

    public boolean getBoolean(String path, boolean def) {
        CommentedConfigurationNode node = getInternal(path);
        if (node == null) return def;
        return node.getBoolean();
    }

    public BigDecimal getBigDecimal(String path) {
        CommentedConfigurationNode node = getInternal(path);
        try {return node.get(BigDecimal.class);} catch (SerializationException e) {return null;}
    }

    public BigDecimal getBigDecimal(String path, BigDecimal def) {
        CommentedConfigurationNode node = getInternal(path);
        try {return node.get(BigDecimal.class);} catch (SerializationException e) {return def;}
    }

    public List<String> getList(String path) {
        return getList(path, String.class);
    }

    public List<String> getKeys(String path) {
        return childrenMap(path).keySet().stream().map(Object::toString).toList();
    }

    public List<CommentedConfigurationNode> childrenList(String path) {
        return getInternal(path).childrenList();
    }

    public Map<Object, CommentedConfigurationNode> childrenMap(String path) {
        return getInternal(path).childrenMap();
    }

    public boolean is(String path) {
        return !getInternal(path).isNull();
    }

    public boolean exists() {
        return file.exists();
    }

    public <T> List<T> getList(String path, Class<T> type) {
        CommentedConfigurationNode node = getInternal(path);
        if (node == null) return new ArrayList<>();

        try {
            List<T> list = node.getList(type);
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        } catch (SerializationException e) {
            return new ArrayList<>();
        }
    }

    public String[] list() {
        return file.list();
    }

    public CustomConfigBuilder save() {
        try {this.loader.save(this.configurationNode);} catch (IOException ignored) {}
        return this;
    }

    public CustomConfigBuilder load() {
        if (this.file != null) {
            if (this.file.exists()) {
                try {this.configurationNode = loader.load();} catch (IOException ignored) {}
            } else {
                this.configurationNode = loader.createNode();
            }
        }
        return this;
    }

    public CommentedConfigurationNode createNode() {
        return loader.createNode();
    }

    public CommentedConfigurationNode getNode(String path) {
        CommentedConfigurationNode node = getInternal(path);
        if (node.virtual()) return null;
        return node;
    }

    private void setInternal(String path, Object value) {
        try { getSplitNode(path, this.configurationNode).set(value); } catch (SerializationException ignored) { }
    }

    private void setInternal(String path, Class<?> type, Object value) {
        try {getSplitNode(path, this.configurationNode).set(type, value);} catch (SerializationException ignored) {}
    }

    private CommentedConfigurationNode getInternal(String path) {
        return getSplitNode(path, this.configurationNode);
    }

    private CommentedConfigurationNode getSplitNode(String path, CommentedConfigurationNode node) {
        if (path == null) {
            return node;
        }
        path = path.startsWith(".") ? path.substring(1) : path;
        return node.node(path.contains(".") ? path.split("\\.") : new Object[]{path});
    }

}