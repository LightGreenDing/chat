//js版本号
var version = 20181117001;

var CONTROLLER_ROOT_PATH = '/webChat';

var generalDataUrl = {};

var chatRoomUrl = {
    find: '/webChat/socket/room/list',
    save: '/webChat/socket/room/add',
    edit: '/webChat/socket/room/update',
    delete: '/webChat/socket/room/delete',
    ban: '/webChat/socket/room/ban',
    list: '/webChat/socket/room/listAllRoom',
    lotteryTypes: '/webChat/socket/room/lotteryTypesByTenantCode'
};

var chatMonitorUrl = {
    save: '/webChat/sensitive/word/add',
    delete: '/webChat/sensitive/word/delete',
    list: '/webChat/sensitive/word/list',
    saveBanned: '/webChat/banned/user/add',
    deleteBanned: '/webChat/banned/user/delete',
    listBanned: '/webChat/banned/user/list',
    recall: '/webChat/chat/record/recall'
};

var tenantUrl = {
    find: '/webChat/tenant/list',
    list: '/webChat/tenant/findListByLotteryType',
    save: '/webChat/tenant/add',
    delete: ''
};
var noticeUrl = {
    find: '/webChat/notice/list',
    save: '/webChat/notice/add',
    delete: '/webChat/notice/delete',
    ban: '/webChat/notice/ban'
};
var chatRoomTypeUrl = {
    find: '/webChat/socket/room/type/list',
    save: '/webChat/socket/room/type/add',
    list: '/webChat/socket/room/type/listAllRoomType',
    delete: ''
};

var userUrl = {
    find: '/webChat/user/findUserByInfo',
    save: '/webChat/user/add',
    modifyPassword: '/webChat/user/modify-password',
    passWord: '/webChat/user/upPassWord',
    delete: '/webChat/user/delete'
};

var systemCfgUrl = {
    find: '/webChat/system/cfg/list',
    save: '/webChat/system/cfg/add',
    delete: ''
};

var ipaddressUrl = {
    find: '/webChat/ipaddress/list',
    save: '/webChat/ipaddress/add',
    delete: '/webChat/ipaddress/delete'
};

var lotteryTypeUrl = {
    find: '/webChat/lottery/type/list',
    save: '/webChat/lottery/type/add',
    delete: '/webChat/lottery/type/delete',
    list: '/webChat/lottery/type/lotteryTypesByTenantCode'
};
var chatRecordUrl = {
    list: '/webChat/chat/record/findChatRecordListByInfo',
    count: '/webChat/chat/record/findChatRecordCount'
};
var betRecordUrl = {
    list: '/webChat/betRecord/list',
    totalCount: '/webChat/betRecord/totalCount'
};
window.lms = {};
window.lms.urlUtil = {
    getQuery: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
};
window.lms.account = {
    getCurrent: function () {
        return JSON.parse(localStorage.getItem("user"));
    },
    isSuperTenant: function () {
        return this.getCurrent().superTenant;
    }
};
window.lms.errorTip = {
    "NOT_LOGIN": "您还未登录",
    "OFFLINE1": "您的账号已经掉线,请重新登录",
    "OFFLINE2": "您的账号已经在其他地方登录"
};

layui.use(['jquery'], function () {
    var $ = layui.jquery;
    $ex.jquery = $;
    $(document).ajaxSend(function (event, request, settings) {
        settings.url = addAjaxUrlPrefix(settings.url);
    })

    /*
     $(document).ajaxComplete(function (event, request, settings) {
     if (settings.showWaitIndex) {
     layer.close(settings.showWaitIndex);
     }
     });

     /!*  $(document).ajaxError(function (event,xhr,options,exc) {

     });*!/
     $(document).ajaxSuccess(function (event, xhr, settings) {
     if (settings.ignoreErrorTip == true) {
     return;
     }
     var resp = xhr.responseJSON;
     if (resp && resp.success == false) {
     var errorMsg = resp.message || '请求出现错误';
     // layer.alert(errorMsg, {title: settings.url, icon: 2});
     console.error(settings.url, resp)
     }
     });*/

});

var $ex = {
    jquery: null,
    ajax: function (settings, showConfirm, confirmTitle) {
        var _this = this;
        var doAjax = function () {
            ajaxBefore(settings);
            var senderSuccessCallback = settings.success;
            var senderErrorCallback = settings.error;
            settings.error = function (resp) {
                ajaxComplete(settings, resp, true);
                if (senderErrorCallback) {
                    senderErrorCallback(resp);
                }
            };
            settings.success = function (resp) {

                var result = ajaxComplete(settings, resp, false);
                if (result === true) {
                    if (senderSuccessCallback) {
                        senderSuccessCallback(resp);
                    }
                }
            };

            _this.jquery.ajax(settings);
        };
        if (showConfirm === true) {
            confirmTitle = confirmTitle || '确认操作';
            window.lms.layer.confirm(confirmTitle, doAjax);
        } else {
            doAjax();
        }
    }
};

var ajaxBefore = function (settings) {
    settings.url = addAjaxUrlPrefix(settings.url);
    if (settings.showWait === true) {
        settings.showWaitIndex = layer.load(2, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
    }
};
var ajaxComplete = function (settings, resp, isError) {
    if (settings.showWaitIndex) {
        layer.close(settings.showWaitIndex);
    }
    if (settings.ignoreErrorTip === true) {
        return true;
    }
    if (isError) {
        layer.alert("请求失败:" + resp.status, {title: "错误信息", icon: 2});
    } else {
        if (resp && resp.code != 200) {

            var isNotLogin = checkNotLogin(resp);
            if (isNotLogin) {
                return;
            }

            var errorMsg = resp.message || '请求出现错误';
            layer.alert(errorMsg, {title: "错误信息", icon: 2});
            console.error(settings.url, resp);
            return false;
        }
    }
    return true;
};

var checkNotLogin = function (resp) {
    if (resp.code == 401) {
        var msg = resp.message;
        if (window.parent) {
            window.parent.toLoginPage(msg)
        } else {
            toLoginPage(msg)
        }
        return true;
    }
    return false;
};

var toLoginPage = function (message) {
    window.location.href = "/login?error=" + message;
};
var addAjaxUrlPrefix = function (url) {

    if (!url) {
        return url;
    }
    if (url.toLowerCase().indexOf("http:") == 0) {
        return url;
    }
    if (url[0] != "/") {
        url = "/" + url;
    }
    return url;
};





