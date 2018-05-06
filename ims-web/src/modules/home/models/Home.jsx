import {actions} from 'mirrorx';
import * as api from "../services/Home";
export default {
    name : "home",
    initialState : {
        userPlan_title:"",
        userPlan_content:"",
        userPlan_level:"2",
        userPlan_levels:{
            red:{
                label:"紧急事件",
                color:"red",
                level:0
            },
            orange:{
                label:"重要事件",
                color:"orange",
                level:1
            },
            green:{
                label:"一般事件",
                color:"green",
                level:2
            }

        },
        on_userPlanS:[],
        end_userPlanS:[],
        userDiary_pages:0,
        userDiary_items:0,
        userDiary_curPage:1,
        userDiaryS_show:[],
        userDiaryS:[],
        userDiary_new:{
            pid:"",
            title:"",
            content:"",
            createDate_s:"",
            updateDate_s:""
        },
        userDiary_title:"",
        userDiary_content:"",
        userLinkS:[],
        new_name:"",
        new_phone:"",
        new_e_mail:"",
        new_job:"",
        new_address:"",
        new_description:"",
        userLink_new:{
            name:"",
            phone:"",
            e_mail:"",
            job:"",
            address:"",
            description:""
        },
        userLinkS_short:[],
        userInfo_change:{
            name:"",
            job:"",
            job_date:"",
            motto:"",
            qq:"",
            e_mail:"",
            address:"",
            phone:"",
            password:"",
            ac_password:""
        },
        userInfo_ablumS:[],
        userInfo_photoS:[],
        modal_userInfo_head_change:false,
        userInfo:{
            name:"",
            job:"",
            job_date:"",
            motto:"",
            qq:"",
            e_mail:"",
            address:"",
            phone:"",
            password:"",
            ac_password:""
        },

        // Img : [
        //     require('E:/Download/photo01.jpg'),
        //     require('E:/Download/photo01.jpg'),
        //     require('E:/Download/photo01.jpg'),
        // ],
        src:require('E:/Download/photo01.jpg'),
        album_src: require('E:/Download/photo01.jpg'),
        info_text01:[
        {"name":"姓名","value":"张雪"},
        {"name":"职业","value":"中学教师"},
        {"name":"从业时间","value":"两年"}
        ],
        motto:[{"name":"签名","value":"最好的生活, 最美的自己"}],
        info_text02:[
        {"name":"电话","value":"18010541632"},
        {"name":"QQ","value":"1982038972"},
        {"name":"E-Mail","value":"1982038972@qq.com"},
        {"name":"地址","value":"四川大学(江安校区)"}
        ]
    },
    reducers : {
        // doc_click(){
        //     // let  {data:{data,success}} = await api.CallApi("ni hao");
        //     // actions.home.save({motto:"nihao a"})
        //     let motto=[{"name":"签名","value":"最好的生活, 最美的自己,sbsbsbs"}];
        //     actions.home.save_1(motto);
        // },
        save(state, data) {
            console.log(state);
            console.log(data);
            return {
                ...state,
                ...data
            }
        }
        
    },
    effects : {
        doc_click(){

        //     let info_text02=[
        //         {"name":"电话","value":"18010541632"},
        //         {"name":"QQ","value":"1982038972"},
        //         {"name":"E-Mail","value":"1982038972@qq.com"},
        //         {"name":"地址","value":"四川大学(望江校区)"}
        //         ];
        //     actions.home.save_1({info_text02});
        //    console.log("success");    
        actions.routing.push({
            pathname: `/document`
        });
        },
        async loadUserInfo(args){
            let {data,data:{success}} = await api.loadUserInfo(args);
            if(success){
                let userInfo=data.userInfo;
                let src = data.userInfo.head_uerl;
                let info_text01 = [];
                info_text01.push({"name":"姓名","value":data.userInfo.name});
                info_text01.push({"name":"职业","value":data.userInfo.job});
                info_text01.push({"name":"从业时间","value":data.userInfo.jobDate});
                let info_text02 = [];
                info_text02.push({"name":"电话","value":data.userInfo.phone})
                info_text02.push({"name":"QQ","value":data.userInfo.qq})
                info_text02.push({"name":"E-Mail","value":data.userInfo.e_mail})
                info_text02.push({"name":"地址","value":data.userInfo.address})
                let motto = [{"name":"签名","value":data.userInfo.motto}]
                actions.home.save({src,info_text01,info_text02,motto,userInfo});
            }
        },
        async changeUserInfo_Img(args){
            let {data,data:{success}} = await api.changeUserInfo_Img(args);
            if(success){
                let src = data.userInfo.head_uerl;
                console.log(src);
                actions.home.save({src});
                return success;

            }
        },
        async changeUserInfo(args){
            let {data:{success}} = await api.changeUserInfo(args);
            if(success){
                let userInfo = args.userInfo;
                actions.home.save({userInfo});
            }
        },
        async getAllUserLinkS(args){
            let {data:{success},data:{userLinkS}} = await api.getAllUserLinkS(args);
            if(success){
                actions.home.save({userLinkS});
            }
        },
        async createUserLink(args){
            let {data:{success},data:{userLink}} = await api.createUserLink(args);
            if(success){
                let userLinkS = args.userLinkS;
                userLinkS.push(userLink);
                let userLink_new={
                    name:"",
                    phone:"",
                    e_mail:"",
                    job:"",
                    address:"",
                    description:""
                }
                let new_name="";
                let new_phone="";
                let new_e_mail="";
                let new_job="";
                let new_address="";
                let new_description="";
                actions.home.save({userLinkS,userLink_new,new_name,new_phone,new_e_mail,new_job,new_address,new_description});

            }
        },
        async deleteUserLink(args){
            let {data:{success}} = await api.deleteUserLink(args);
            if(success){
                let count =0;
                let _count=0;
                let userLinkS = [];
                actions.home.save({userLinkS});
                userLinkS = args.userLinkS;
                userLinkS.map(function(userLink){
                    if(userLink.pid==args.userLinkId){
                        _count=count;
                    }
                    count++;
                })
                userLinkS.splice(_count,1);
                console.log(userLinkS);
                actions.home.save({userLinkS});
                alert("删除成功");
            }
        },
        async saveDiary(args){
            if(args.diaryId==""){
                let {data,data:{success}} = await api.createDiary(args);
                if(success){
                    // let userDiary_pages=args.userDiary_pages;
                    // let userDiary_items=args.userDiary_items;
                    // if(userDiary_items+1==8){
                    //     userDiary_pages++;
                    //     userDiary_items=1;
                    // }
                    let userDiary_new = data.userDiary;
                    actions.home.save({userDiary_new});
                    alert("已保存");
                }
            }else{
                let {data,data:{success}} = await api.saveDiary(args);
                if(success){
                    let userDiary_new = data.userDiary;
                    actions.home.save({userDiary_new});
                    alert("已保存");
                }
            }
        },
        async changDiaryPage(args){
            let  {eventKey,userDiaryS,userDiary_pages,userDiary_items} = args;
            let userDiaryS_show=[];
            let userDiary_curPage=eventKey;
            if(eventKey<userDiary_pages){
                for(let i=0;i<7;i++){
                    userDiaryS_show.push(userDiaryS[(eventKey-1)*7+i]);
                }
            }else{
                for(let i=0;i<userDiary_items;i++){
                    userDiaryS_show.push(userDiaryS[(eventKey-1)*7+i]);
                }
            }
            actions.home.save({userDiaryS_show,userDiary_curPage});
            
        },
        async getUserDiaryS(args){
            let {data,data:{success}} = await api.getUserDiaryS(args);
            if(success){
                let {userDiarieS:userDiaryS,pages:userDiary_pages,items:userDiary_items} = data;
                actions.home.save({userDiaryS,userDiary_pages,userDiary_items});
                return success;
            }
        },
        async savePlan(args){
            let {data,data:{success}} = await api.savePlan(args);
            if(success){
                let {userPlan} = data;
                let on_userPlanS =[];
                actions.home.save({on_userPlanS});
                on_userPlanS= args.on_userPlanS;
                on_userPlanS.splice(0,0,userPlan);
                console.log(on_userPlanS);
                actions.home.save({on_userPlanS});
            }
        },
        async endPlan(args){
            let {data:{success}} = await api.endPlan(args);
            if(success){
                let userPlan=args.userPlan;
                let on_userPlanS=[];
                let end_userPlanS=[];
                actions.home.save({on_userPlanS,end_userPlanS});
                on_userPlanS=args.on_userPlanS;
                end_userPlanS=args.end_userPlanS;
                let count=0;
                let _count=0;
                on_userPlanS.map(function(_userplan){
                    if(_userplan.pid==userPlan.pid){
                        _count=count;
                    }
                    count++;
                });
                on_userPlanS.splice(_count,1);
                end_userPlanS.unshift(userPlan);
                actions.home.save({on_userPlanS,end_userPlanS});
            }
        },
        async getUserPlanS(args){
            let {data,data:{success}} = await api.getUserPlanS(args);
            if(success){
                let {on_userPlanS,end_userPlanS} = data;
                actions.home.save({on_userPlanS,end_userPlanS});
            }
        }

    }
}