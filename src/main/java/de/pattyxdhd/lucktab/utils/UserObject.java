package de.pattyxdhd.lucktab.utils;

import com.google.common.collect.Maps;
import de.pattyxdhd.lucktab.LuckTab;
import lombok.Getter;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class UserObject {

    @Getter
    private final static Map<UUID, UserObject> userObjects = Maps.newHashMap();

    @Getter
    private final UUID uuid;
    @Getter
    private final String group;
    private String prefix;
    private String suffix;
    private Integer weight;
    private String chatPrefix;
    private String chatSuffix;

    public UserObject(UUID uuid, String group, String prefix, String suffix, int weight, String chatPrefix, String chatSuffix) {
        this.uuid = uuid;
        this.group = group;
        this.prefix = prefix;
        this.suffix = suffix;
        this.weight = weight;
        this.chatPrefix = chatPrefix;
        this.chatSuffix = chatSuffix;
    }

    public String getTabPrefix() {
        if (prefix.length() > 16) {
            return prefix.replace('&', '§').substring(0, 15);
        } else {
            return prefix.replace('&', '§');
        }
    }

    public String getTabSuffix() {
        if (suffix.length() > 16) {
            return suffix.replace('&', '§').substring(0, 15);
        } else {
            return suffix.replace('&', '§');
        }
    }

    public UserObject setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getPrefix() {
        return prefix.replace('&', '§');
    }

    public UserObject setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getSuffix() {
        return "§r" + suffix.replace('&', '§');
    }

    public UserObject setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public String getWeight() {
        NumberFormat numberFormat = new DecimalFormat("0000");
        return numberFormat.format(this.weight);
    }

    public UserObject setChatPrefix(String chatPrefix) {
        this.chatPrefix = chatPrefix;
        return this;
    }

    public String getChatPrefix() {
        String chatPrefix = this.chatPrefix.replace('&', '§');
        if (LuckTab.getInstance().getConfig().getBoolean("usePrefixFromTablist", true)){
            if (chatPrefix.isEmpty()){
                return getTabPrefix();
            } else {
                return chatPrefix;
            }
        } else {
            return chatPrefix;
        }
    }

    public UserObject setChatSuffix(String chatSuffix) {
        this.chatSuffix = chatSuffix;
        return this;
    }

    public String getChatSuffix() {
        String chatSuffix = this.chatSuffix.replace('&', '§');
        if (LuckTab.getInstance().getConfig().getBoolean("useSuffixFromTablist", true)){
            if (chatSuffix.isEmpty()){
                return getTabSuffix();
            } else {
                return chatSuffix;
            }
        } else {
            return chatSuffix;
        }
    }

    public static UserObject getUserObject(UUID uuid) {
        return userObjects.get(uuid);
    }

    public boolean isInGroup(String group) {
        User user = LuckTab.getLuckPermsApi().getUserManager().getUser(this.uuid);
        assert user != null;
        Set<String> groups = user.getNodes().stream()
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .map(InheritanceNode::getGroupName)
                .collect(Collectors.toSet());

        return groups.contains(group);
    }

    public static UserObject convert(final Player player) {

        User user = LuckTab.getLuckPermsApi().getPlayerAdapter(Player.class).getUser(player);
        Group group = LuckTab.getLuckPermsApi().getGroupManager().getGroup(user.getPrimaryGroup());
        CachedMetaData metaData = user.getCachedData().getMetaData();

        SortedMap<Integer, String> prefixes = metaData.getPrefixes();
        String highestPrefix = null;
        if (!prefixes.isEmpty()) {
            int highestWeightPrefix = prefixes.lastKey();
            highestPrefix = prefixes.get(highestWeightPrefix);
        }
        if (highestPrefix == null) {
            highestPrefix = "";
        }

        SortedMap<Integer, String> suffixes = metaData.getSuffixes();
        String highestSuffix = null;
        if (!suffixes.isEmpty()) {
            int highestWeightSuffix = suffixes.lastKey();
            highestSuffix = suffixes.get(highestWeightSuffix);
        }
        if (highestSuffix == null) {
            highestSuffix = "";
        }

        assert group != null;
        OptionalInt optionalInt = group.getWeight();
        int groupWeight = 9999;
        if (optionalInt.isPresent()) {
            groupWeight = optionalInt.getAsInt();
        }

        String chatPrefixValue = metaData.getMetaValue("chatprefix");
        String chatSuffixValue = metaData.getMetaValue("chatsuffix");

        String chatPrefix;
        if (chatPrefixValue != null) {
            chatPrefix = chatPrefixValue;
        } else {
            chatPrefix = "";
        }

        String chatSuffix;
        if (chatSuffixValue != null) {
            chatSuffix = chatSuffixValue;
        } else {
            chatSuffix = "";
        }

        return new UserObject(player.getUniqueId(), user.getPrimaryGroup(), highestPrefix, highestSuffix, groupWeight, chatPrefix, chatSuffix);
    }

}
