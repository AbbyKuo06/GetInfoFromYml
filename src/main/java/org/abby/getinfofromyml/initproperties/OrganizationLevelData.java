package org.abby.getinfofromyml.initproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * 組織階層設定檔(form yml)
 */
@Component
@ConfigurationProperties(prefix = "config.organization")
public class OrganizationLevelData {

    private List<OrganizationLevelList> organizationLevelList;

    public List<OrganizationLevelList> getOrganizationLevelList() {
        return organizationLevelList;
    }

    public void setOrganizationLevelList(List<OrganizationLevelList> organizationLevelList) {
        this.organizationLevelList = organizationLevelList;
    }

    /**
     * 額外計算的參數,不會在yml設定 Start
     * -- GETTER --
     * 組織階層設定_最小階層的level值
     */

    private Long minLevel;
    /**
     * -- GETTER --
     * 組織階層設定_最大階層的level值
     */
    private Long maxLevel;

    public Long getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Long minLevel) {
        this.minLevel = minLevel;
    }

    public Long getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Long maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * 額外計算的參數,不會在yml設定 End
     */


    public static class OrganizationLevelList {
        private String key;
        private OrganizationLevel organizationLevel;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public OrganizationLevel getOrganizationLevel() {
            return organizationLevel;
        }

        public void setOrganizationLevel(OrganizationLevel organizationLevel) {
            this.organizationLevel = organizationLevel;
        }
    }

    public static class OrganizationLevel {
        private Long level;
        private String key;
        private String description;

        public Long getLevel() {
            return level;
        }

        public void setLevel(Long level) {
            this.level = level;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    @PostConstruct
    public void init() {
        minLevel = this.organizationLevelList.stream()
                .map(OrganizationLevelList::getOrganizationLevel)
                .mapToLong(OrganizationLevel::getLevel)
                .min()
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        maxLevel = this.organizationLevelList.stream()
                .map(OrganizationLevelList::getOrganizationLevel)
                .mapToLong(OrganizationLevel::getLevel)
                .max()
                .orElseThrow(() -> new NoSuchElementException("No value present"));
    }

    /**
     * Method to check if the level is within the acceptable range
     */
    public void checkLevelValidity(Long level) {
        if (level < minLevel || level > maxLevel) {
            throw new IllegalArgumentException("Not in organizationLevelData, Level not found: " + level);
        }
    }

    /**
     * Method to check if the organization is at the target level
     */
    public boolean isAtTargetLevel(Long entityLevel, Long level) {
        return entityLevel.equals(level);
    }

    /**
     * Method to check if the organization is at the top level
     */
    public boolean isAtTopLevel(Long entityLevel) {
        return entityLevel.equals(maxLevel);
    }


    public void soutOrganizationLevels(OrganizationLevelData organizationLevelData) {
        organizationLevelData.getOrganizationLevelList().stream()
                .forEach(level -> {
                    System.out.println("Sout organization level:");
                    System.out.println("Key : "+level.getKey());
                    System.out.println("OrganizationLevel Key : "+level.getOrganizationLevel().getKey());
                    System.out.println("OrganizationLevel Level : "+level.getOrganizationLevel().getLevel());
                    System.out.println("OrganizationLevel Description : "+level.getOrganizationLevel().getDescription());
                });
    }
}
