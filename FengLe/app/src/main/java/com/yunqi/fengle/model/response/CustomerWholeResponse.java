package com.yunqi.fengle.model.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunqi.fengle.model.response.model.ContactEntity;
import com.yunqi.fengle.model.response.model.HuikuanInfoEntity;
import com.yunqi.fengle.model.response.model.InvoceInfoEntity;
import com.yunqi.fengle.model.response.model.TuihuoInfoEntity;
import com.yunqi.fengle.model.response.model.VisitPlanEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-04-17 22:12
 * @Description: 客户全貌
 */

public class CustomerWholeResponse implements MultiItemEntity,Cloneable {

    /**
     * id : 17629
     * name : （原药）杭州中隆化工科技有限公司——江媛
     * company_name : （原药）杭州中隆化工科技有限公司——江媛
     * phone :
     * position :
     * type : 3
     * user_code : 2003211
     * custom_code : 0106010022
     * dept_code : 0402
     * area_code : 020306
     * fahuo_amount : 40
     * tuihuo_amount : 225
     * huikuan_amount : 122131
     * visit_plan : {"last_visite_plan":{"id":22,"client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","plan_time":"2017-04-18 00:00:00","start_time":null,"end_time":null,"responsible_name":"qqqq","create_time":"2017-04-18 21:56:31","userid":13,"reason":"qqqq","status":1,"client_code":"0106010022"},"visite_plan_count":1}
     * huikuan_info : {"last_huikuan":{"hk_id":91,"client_code":"0106010022","huikuan_time":"2017-04-18 00:00:00","huikuan_type":"农药款","client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","huikuan_amount":1111,"remark":"11111","images":null,"create_time":"2017-04-18 21:41:44","userid":13,"status":2,"person_code":"2003211","huikuan_name":"1111","pay_type":"现金结算","huikuan_type_code":"1","pay_type_code":"1","confirm_amount":1111,"bank_caption_code":"10020101"},"huikuan_count":7}
     * tuihuo_info : {"last_tuihuo":{"id":5,"userid":13,"create_time":"2017-04-12 09:01:06","order_code":"FLNH201704120901062886","status":2,"client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","remark":null,"client_code":"0106010022","order_type":"THSQ","deal_depth":1},"tuihuo_count":2}
     * invoce_info : {"last_invoice":{"id":3,"userid":13,"create_time":"2017-04-16 11:24:22","order_code":"FLNH201704161124223321","status":3,"client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","remark":"","client_code":"0106010022","order_type":"KPSQ","deal_depth":2},"invoice_count":1}
     */

    private int id;
    private String name;
    private String company_name;
    private String phone;
    private String position;
    private int type;
    private int user_code;
    private String custom_code;
    private String dept_code;
    private String area_code;
    private int fahuo_amount;
    private int tuihuo_amount;
    private int huikuan_amount;


    private List<ContactEntity> linkmans;
    /**
     * last_visite_plan : {"id":22,"client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","plan_time":"2017-04-18 00:00:00","start_time":null,"end_time":null,"responsible_name":"qqqq","create_time":"2017-04-18 21:56:31","userid":13,"reason":"qqqq","status":1,"client_code":"0106010022"}
     * visite_plan_count : 1
     */

    private VisitPlanEntity visit_plan;
    /**
     * last_huikuan : {"hk_id":91,"client_code":"0106010022","huikuan_time":"2017-04-18 00:00:00","huikuan_type":"农药款","client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","huikuan_amount":1111,"remark":"11111","images":null,"create_time":"2017-04-18 21:41:44","userid":13,"status":2,"person_code":"2003211","huikuan_name":"1111","pay_type":"现金结算","huikuan_type_code":"1","pay_type_code":"1","confirm_amount":1111,"bank_caption_code":"10020101"}
     * huikuan_count : 7
     */

    private HuikuanInfoEntity huikuan_info;
    /**
     * last_tuihuo : {"id":5,"userid":13,"create_time":"2017-04-12 09:01:06","order_code":"FLNH201704120901062886","status":2,"client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","remark":null,"client_code":"0106010022","order_type":"THSQ","deal_depth":1}
     * tuihuo_count : 2
     */

    private TuihuoInfoEntity tuihuo_info;
    /**
     * last_invoice : {"id":3,"userid":13,"create_time":"2017-04-16 11:24:22","order_code":"FLNH201704161124223321","status":3,"client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","remark":"","client_code":"0106010022","order_type":"KPSQ","deal_depth":2}
     * invoice_count : 1
     */

    private InvoceInfoEntity invoce_info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_code() {
        return user_code;
    }

    public void setUser_code(int user_code) {
        this.user_code = user_code;
    }

    public String getCustom_code() {
        return custom_code;
    }

    public void setCustom_code(String custom_code) {
        this.custom_code = custom_code;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public int getFahuo_amount() {
        return fahuo_amount;
    }

    public void setFahuo_amount(int fahuo_amount) {
        this.fahuo_amount = fahuo_amount;
    }

    public int getTuihuo_amount() {
        return tuihuo_amount;
    }

    public void setTuihuo_amount(int tuihuo_amount) {
        this.tuihuo_amount = tuihuo_amount;
    }

    public int getHuikuan_amount() {
        return huikuan_amount;
    }

    public void setHuikuan_amount(int huikuan_amount) {
        this.huikuan_amount = huikuan_amount;
    }

    public VisitPlanEntity getVisit_plan() {
        return visit_plan;
    }

    public void setVisit_plan(VisitPlanEntity visit_plan) {
        this.visit_plan = visit_plan;
    }

    public HuikuanInfoEntity getHuikuan_info() {
        return huikuan_info;
    }

    public void setHuikuan_info(HuikuanInfoEntity huikuan_info) {
        this.huikuan_info = huikuan_info;
    }

    public TuihuoInfoEntity getTuihuo_info() {
        return tuihuo_info;
    }

    public void setTuihuo_info(TuihuoInfoEntity tuihuo_info) {
        this.tuihuo_info = tuihuo_info;
    }

    public InvoceInfoEntity getInvoce_info() {
        return invoce_info;
    }

    public void setInvoce_info(InvoceInfoEntity invoce_info) {
        this.invoce_info = invoce_info;
    }






    private int modelType;

    public static final int TYPE_CONTENT = 0x01;//title
    public static final int TYPE_CONTACT = 0x02;//联系人
    public static final int TYPE_VISIT = 0x03;//拜访
    public static final int TYPE_SALE = 0x04;//销售订单
    public static final int TYPE_RETURN = 0x05;//回款
    public static final int TYPE_REFUND = 0x06;//退款
    public static final int TYPE_INVOICE = 0x07;//开票
    public static final int TYPE_EXPENS = 0x08;//费用报销
    public static final int TYPE_OTHER = 0x09;//其他

    public CustomerWholeResponse(int type) {
        this.modelType = type;
    }

    public CustomerWholeResponse() {
    }
    @Override
    public int getItemType() {
        return modelType;
    }

    public void setItemType(int type) {
        this.modelType = type;
    }

    public static List<CustomerWholeResponse> getMultiItemList(CustomerWholeResponse bean) {
        CustomerWholeResponse content = bean.clone(TYPE_CONTENT);
        CustomerWholeResponse contact = bean.clone(TYPE_CONTACT);
        CustomerWholeResponse visit = bean.clone(TYPE_VISIT);
//        CustomerWholeMultiItem sale = new CustomerWholeMultiItem(TYPE_SALE);
        CustomerWholeResponse returN = bean.clone(TYPE_RETURN);
        CustomerWholeResponse refund = bean.clone(TYPE_REFUND);
        CustomerWholeResponse invoice = bean.clone(TYPE_INVOICE);
        CustomerWholeResponse expens = bean.clone(TYPE_EXPENS);
        CustomerWholeResponse other = bean.clone(TYPE_OTHER);
        List<CustomerWholeResponse> itemList = new ArrayList<>();
        itemList.add(content);//content
        itemList.add(contact);//联系人
        itemList.add(visit);//拜访
//        itemList.add(sale);
        itemList.add(returN);//回款
        itemList.add(refund);//退款
        itemList.add(invoice);//开票
//        itemList.add(expens);//费用报销
        itemList.add(other);//其他
        return itemList;
    }

    public CustomerWholeResponse clone(int type) {
        CustomerWholeResponse response = (CustomerWholeResponse) clone();
        response.setItemType(type);
        return response;
    }

    @Override
    public Object clone() {
        CustomerWholeResponse bean = null;
        try{
            bean = (CustomerWholeResponse)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<ContactEntity> getLinkmans() {
        return linkmans;
    }

    public void setLinkmans(List<ContactEntity> linkmans) {
        this.linkmans = linkmans;
    }
}
