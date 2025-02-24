package com.itsjambo.thunderspacecustomitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage("&#00FFC8&lT&#00F9CA&lh&#01F3CC&lu&#01EECE&ln&#02E8D0&ld&#02E2D2&le&#02DCD4&lr&#03D7D6&lS&#03D1D8&lp&#04CBDA&la&#04C5DC&lc&#04BFDE&le&#05BAE0&lC&#05B4E2&lu&#06AEE5&ls&#06A8E7&lt&#07A3E9&lo&#079DEB&lm&#0797ED&lI&#0891EF&lt&#088BF1&le&#0986F3&lm&#0980F5&ls &#0A74F9&lH&#0A6FFB&le&#0B69FD&ll&#0B63FF&lp");
        sender.sendMessage("&f/tsci-create [name] [description] [material] [enchantment]:[level] - &#00FFC8C&#01F7CBr&#01EFCEe&#02E8D0a&#02E0D3t&#03D8D6e &#04C8DBa &#05B9E1c&#06B1E4u&#06A9E6s&#07A1E9t&#079AECo&#0892EFm &#0982F4i&#097AF7t&#0A73FAe&#0A6BFCm&#0B63FF.");
        sender.sendMessage("&f/tsci-reload - &#00FFC8R&#00FACAe&#01F5CCl&#01F0CDo&#01EBCFa&#02E6D1d &#02DCD4t&#03D7D6h&#03D2D8e &#04C8DCp&#04C3DDl&#05BEDFu&#05B9E1g&#05B4E3i&#06AEE4n &#06A4E8c&#079FEAo&#079AEBn&#0795EDf&#0890EFi&#088BF1g&#0986F3u&#0981F4r&#097CF6a&#0A77F8t&#0A72FAi&#0A6DFBo&#0B68FDn&#0B63FF.");
        sender.sendMessage("&f/tsci-3x3 [name] [description] [material] - &#00FFC8C&#00FCC9r&#00F9CAe&#01F6CBa&#01F4CCt&#01F1CDe &#01EBCFa &#02E5D1c&#02E3D2u&#02E0D3s&#02DDD4t&#03DAD5o&#03D7D6m &#03D2D8i&#03CFD9t&#04CCDAe&#04C9DBm &#04C3DDt&#04C1DEh&#05BEDFa&#05BBE0t &#05B5E2b&#05B2E3r&#06B0E4e&#06ADE5a&#06AAE6k&#06A7E7s &#07A1E9b&#079FEAl&#079CEBo&#0799ECc&#0796EDk&#0893EEs &#088EF0i&#088BF1n &#0985F3a &#097FF53&#097DF6x&#097AF73 &#0A74F9r&#0A71FAa&#0A6EFBd&#0A6CFCi&#0B69FDu&#0B66FEs&#0B63FF.");
        sender.sendMessage("&f/tsci-5x5 [name] [description] [material] - &#00FFC8C&#00FCC9r&#00F9CAe&#01F6CBa&#01F4CCt&#01F1CDe &#01EBCFa &#02E5D1c&#02E3D2u&#02E0D3s&#02DDD4t&#03DAD5o&#03D7D6m &#03D2D8i&#03CFD9t&#04CCDAe&#04C9DBm &#04C3DDt&#04C1DEh&#05BEDFa&#05BBE0t &#05B5E2b&#05B2E3r&#06B0E4e&#06ADE5a&#06AAE6k&#06A7E7s &#07A1E9b&#079FEAl&#079CEBo&#0799ECc&#0796EDk&#0893EEs &#088EF0i&#088BF1n &#0985F3a &#097FF55&#097DF6x&#097AF75 &#0A74F9r&#0A71FAa&#0A6EFBd&#0A6CFCi&#0B69FDu&#0B66FEs&#0B63FF.");
        sender.sendMessage("&f/tsci-give [nickname] [id] - &#00FFC8G&#00FBCAi&#01F6CBv&#01F2CDe &#02E9D0a &#02E1D3c&#02DCD4u&#03D8D6s&#03D4D7t&#03CFD9o&#04CBDAm &#04C2DDi&#05BEDFt&#05BAE0e&#05B5E2m &#06ADE5t&#06A8E7o &#07A0EAa &#0797EDp&#0893EEl&#088EF0a&#088AF1y&#0986F3e&#0981F4r &#0979F7b&#0A74F9y &#0A6CFCI&#0B67FDD&#0B63FF.");
        sender.sendMessage("&f/tsci-addenchantment [id] [enchantment]:[level] - &#00FFC8A&#00FBC9d&#01F7CBd &#01EFCEa&#01EACFn &#02E2D2e&#02DED4n&#03DAD5c&#03D6D6h&#03D2D8a&#03CED9n&#04CADBt&#04C6DCm&#04C1DEe&#05BDDFn&#05B9E1t &#06B1E4t&#06ADE5o &#06A5E8a&#07A1E9n &#0798ECe&#0894EEx&#0890EFi&#088CF1s&#0888F2t&#0984F3i&#0980F5n&#097CF6g &#0A73F9i&#0A6FFBt&#0A6BFCe&#0B67FEm&#0B63FF.");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return Collections.emptyList();
    }
}