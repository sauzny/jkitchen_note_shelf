# javafx demo03

## 一、使用 `idea` 创建项目

使用 `idea`, java版本为 `openjdk11`

在创建 `maven` 项目时，选择 `Add Archetype`，添加

| name | value |
| -- | -- |
| GroupId | org.openjfx |
| ArtifactId | javafx-archetype-fxml |
| version | 0.0.6 |

这个可以到仓库中查看具体版本

`https://mvnrepository.com/artifact/org.openjfx/javafx-archetype-fxml`

按其他 `maven`工程 创建就可以了，最后修改 `pom.xml` 中的 `org.openjfx`依赖 版本号就可以了。

## 二、打包

修改 `pom.xml` 中的 `javafx-maven-plugin`

```xml
<plugin>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
    <version>0.0.6</version>
    <executions>
        <execution>
            <!-- Default configuration for running -->
            <!-- Usage: mvn clean javafx:run -->
            <!-- Usage: mvn clean javafx:jlink -->
            <id>default-cli</id>
            <configuration>
                <stripDebug>true</stripDebug>
                <compress>2</compress>
                <noHeaderFiles>true</noHeaderFiles>
                <noManPages>true</noManPages>
                <launcher>launcher</launcher>
                <jlinkImageName>output</jlinkImageName>
                <jlinkZipName>outputzip</jlinkZipName>
                <mainClass>org.example.App</mainClass>
            </configuration>
        </execution>
    </executions>
</plugin>
```

在 `idea` 右侧的 `maven工具栏` 中，使用 `javafx:jlink` 打包，生成 `target\outputzip.zip`

解压 `outputzip.zip` 之后，`outputzip\bin\launcher.bat` 为可执行文件
