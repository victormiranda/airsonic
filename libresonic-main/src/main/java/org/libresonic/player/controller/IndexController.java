package org.libresonic.player.controller;

import org.libresonic.player.Logger;
import org.libresonic.player.domain.User;
import org.libresonic.player.domain.UserSettings;
import org.libresonic.player.service.SecurityService;
import org.libresonic.player.service.SettingsService;
import org.libresonic.player.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {


    private static final Logger LOG = Logger.getLogger(IndexController.class);

    @Autowired
    private SecurityService securityService;
    @Autowired
    private SettingsService settingsService;

    @RequestMapping(method = { RequestMethod.GET})
    public ModelAndView index(HttpServletRequest request) {
        ControllerUtils.updatePortAndContextPath(request,settingsService);
        UserSettings userSettings = settingsService.getUserSettings(securityService.getCurrentUsername(request));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("showRight", userSettings.isShowNowPlayingEnabled() || userSettings.isShowChatEnabled());
        map.put("autoHidePlayQueue", userSettings.isAutoHidePlayQueue());
        map.put("listReloadDelay", userSettings.getListReloadDelay());
        map.put("keyboardShortcutsEnabled", userSettings.isKeyboardShortcutsEnabled());
        map.put("showSideBar", userSettings.isShowSideBar());
        map.put("brand", settingsService.getBrand());
        return new ModelAndView("index", "model", map);
    }


}
