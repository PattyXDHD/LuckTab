package de.pattyxdhd.lucktab.config;

import de.pattyxdhd.lucktab.LuckTab;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {

    private LuckTab luckTab;
    private FileConfiguration fileConfiguration;
    private File file;

    public ConfigManager(final LuckTab luckTab){
        this.luckTab = luckTab;
        this.fileConfiguration = luckTab.getConfig();
        file = new File(luckTab.getDataFolder().getPath(), "config.yml");
    }

    public ConfigManager copyDefaults(){

        if (!file.exists()) {
            luckTab.saveDefaultConfig();
        }

        luckTab.reloadConfig();
        fileConfiguration = luckTab.getConfig();

        return this;
    }

    public void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFileConfiguration(){
        return fileConfiguration;
    }

    public FormatableString getString(final String path){
        return new FormatableString(fileConfiguration.getString(path));
    }

    public Integer getInt(final String path){
        return fileConfiguration.getInt(path);
    }

    public Boolean getBoolean(final String path){
        return fileConfiguration.getBoolean(path);
    }

    public Double getDouble(final String path){
        return fileConfiguration.getDouble(path);
    }

    public List<String> getStringList(final String path){
        return fileConfiguration.getStringList(path);
    }

    public ItemStack getItemStack(final String path){
        return fileConfiguration.getItemStack(path);
    }

    public void setString(final String path, final String value){
        fileConfiguration.set(path, value);
        save();
    }

    public void setInt(final String path, final Integer value){
        fileConfiguration.set(path, value);
        save();
    }

    public void setBoolean(final String path, final Boolean value){
        fileConfiguration.set(path, value);
        save();
    }

    public void setDouble(final String path, final Double value){
        fileConfiguration.set(path, value);
        save();
    }

    public void setStringList(final String path, final List<String> value){
        fileConfiguration.set(path, value);
        save();
    }

    public void setItemStack(final String path, final ItemStack value){
        fileConfiguration.set(path, value);
        save();
    }



    public boolean exist(final String path){
        return fileConfiguration.isSet(path);
    }


}
