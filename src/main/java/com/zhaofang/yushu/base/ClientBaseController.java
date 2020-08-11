//package com.zhaofang.yushu.base;
//
//public class ClientBaseController extends BaseController {
//
//    public ClientBaseController() {
//    }
//
//    protected User getCurrentUser() {
//        return this.getToken().getUser();
//    }
//
//    protected StatelessToken getToken() {
//        return TicketMap.getToken(SecurityUtils.getSubject().getPrincipal().toString());
//    }
//
//    protected Business getBusiness() {
//        return this.getToken().getBusiness();
//    }
//
//    protected String getBusinessTreeId(String businessTreeId) {
//        String userBusinessTreeId = this.getCurrentUser().getBusinessTreeId();
//        if(businessTreeId != null && !"".equals(businessTreeId)) {
//            if(businessTreeId.contains(userBusinessTreeId)) {
//                return businessTreeId;
//            } else {
//                throw new ServiceException(SystemErrMsg.ERR_1104);
//            }
//        } else {
//            return userBusinessTreeId;
//        }
//    }
//
//
//}
