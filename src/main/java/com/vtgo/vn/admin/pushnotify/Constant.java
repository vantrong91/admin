/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.pushnotify;

/**
 *
 * @author Trong Van
 */
public class Constant {

    public static class NOTIFY_TYPE {

        public static final int EMAIL = 1;
        public static final int FCM_PUSH = 2;
    }

    public static class RECEIVE_TYPE {

        public static final int DRIVER = 1;
        public static final int OWNER = 2;
        public static final int VEHICLE_OWNER = 3;
    }

    public static class QUEUE {

        public static final String RABBITMQ_EXCHANGE = "adminevt";
        public static final String KEY_CHANNEL_PUSH_FROM_ADMIN = "admin_push_noti";
    }

    public static class DRIVER_NOTIFY_TYPE {

        public static final String TYPE0 = "0";// 0 có đơn hàng mới
        public static final String TYPE1 = "1";// 1 : chủ hàng phản hồi báo giá
        public static final String TYPE2 = "2";//2 : chủ hàng từ chối báo giá
        public static final String TYPE3 = "3";//3 : Đơn hàng cần xác định chuyến
        public static final String TYPE4 = "4";//4: "Chủ hàng vừa chọn báo giá cần đặt xe"
        public static final String TYPE5 = "5";//5:"Chinh sua don hang"
        public static final String GOOD_OWNER_PAID_ORDER = "6";//6: CHu hang thanh toan tien hang
        public static final String TYPE7 = "7";//7: admin thong bao chu hang thanh toan tien hang
        public static final String TYPE8 = "8";//8: yc phạt lái xe
        public static final String TYPE9 = "9";//chu xe gui lic trinh
        public static final String TYPE10 = "10";//Chu hang push sang hoan thanh don hang khong phat sinh van de phat 
        public static final String TYPE11 = "11";//11: recurring Push notify gan den thoi gian boc hang 
        public static final String TYPE12 = "12";//12: Het han giay to
        public static final String TYPE13 = "13";//13: chu hang dong y thay doi bao gia
        public static final String TYPE14 = "14";//14: chu hang hy don hang khi khong dong y thay doi bao gia
        public static final String ACCOUNT_RECHARGE = "15";//15: Lai xe nap tien
        public static final String ACCOUNT_WITHDRAWAL = "16";//16: Lai xe rut tien
    }

    public static class OWNER_NOTIFY_TYPE {

        public static final String TYPE0 = "0";//  0 cho thông báo báo giá mới
        public static final String TYPE1 = "1";// 1 : lái xe thông báo sự cố
        public static final String TYPE2 = "2";// 2 : thông báo chương trình khuyến mãi
        public static final String TYPE3 = "3";
        public static final String TYPE4 = "4";//4: "thêm cái là tài xế vừa xác nhận chuyến"
        public static final String TYPE5 = "5";//5: tài thuc hien chuyen
        public static final String TYPE6 = "6";//6: Boc hang xong
        public static final String TYPE7 = "7";//7: báo chủ hàng chuyen hang xong
        public static final String TYPE8 = "8";//8: Thong bao cac su co lien quan toi don hang
        public static final String TYPE9 = "9";//9: thông báo xác nhận thanh toán bảo hiểm
        public static final String TYPE10 = "10";//10: Bao loi don hang
        public static final String TYPE11 = "11";//11: CHu xe, lai xe Bao gia lai
        public static final String TYPE12 = "12";//12: CHu xe, lai xe Bao gia lai khi chinh sua don hang
        public static final String TYPE13 = "13";//13: CHu xe, lai xe Chap nhan thay doi don hang
        public static final String TYPE14 = "14";//14: CHu xe, lai xe khong dong y thay doi don hang
        public static final String TYPE15 = "15";//15: CHu xe huy chuyen gui toi chu hang
        public static final String TYPE16 = "16";//16: tai xe chap nhan phat tien hong hang
        public static final String TYPE17 = "17";//17: tai xe dieu chinh phat tien hong hang
        public static final String TYPE18 = "18";//18: lai xe huy bao gia
        public static final String ACCOUNT_RECHARGE = "19";//19: chu hang nap tien
        public static final String ACCOUNT_WITHDRAWAL = "20";//19: chu hang rut tien
    }

    public static class VEHICLE_OWNER_NOTIFY_TYPE {

        public static final String TYPE0 = "0";// 0 có đơn hàng mới
        public static final String TYPE1 = "1";// 1 : chủ hàng phản hồi báo giá
        public static final String TYPE2 = "2";//2 : chủ hàng từ chối báo giá
        public static final String TYPE3 = "3";//3 : Đơn hàng cần xác định chuyến
        public static final String TYPE4 = "4";//4: "Chủ hàng vừa chọn báo giá cần đặt xe"
        public static final String TYPE5 = "5";//5:"Chinh sua don hang"
        public static final String TYPE6 = "6";//6: CHu hang thanh toan tien hang
        public static final String TYPE7 = "7";//7: admin thong bao chu hang thanh toan tien hang
        public static final String TYPE8 = "8";//8: yc phạt lái xe
        public static final String TYPE9 = "9";//9: gui lich tring toi tai xe
        public static final String TYPE10 = "10";//Chu hang push sang hoan thanh don hang khong phat sinh van de phat
        public static final String TYPE11 = "11";//11: recurring Push notify gan den thoi gian boc hang 
        public static final String TYPE12 = "12";//12: Het han giay to
        public static final String TYPE13 = "13";//13: Xac nhan chuyen giu tai xe
        public static final String TYPE14 = "14";//14: Huy chuyen giu tai xe
        public static final String TYPE15 = "15";//15: gui thong bao thay doi tai xe
        public static final String TYPE16 = "16";//16: chu xem dieu chinh lich trinh dang ky rong gui cho tai xe
        public static final String TYPE17 = "17";//17: Tai xe xac nhan chuyen cho don hang
        public static final String TYPE18 = "18";//18: Tai xe thuc hien bao gia cho don hang
        public static final String TYPE19 = "19";//18: Tai xe thuc hien chuyen cho don hang
        public static final String ACCOUNT_RECHARGE = "20";//20: chu xe nap tien
        public static final String ACCOUNT_WITHDRAWAL = "21";//21: chu xe rut tien
    }
}
