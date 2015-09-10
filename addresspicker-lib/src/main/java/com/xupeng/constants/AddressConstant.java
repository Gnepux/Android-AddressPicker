package com.xupeng.constants;

/**
 * 地址的常量类
 */
public class AddressConstant {

    public static String[] provinceList = new String[] {
        "江苏省",
        "浙江省",
    };

    public static String[][] cityList = new String[][] {
        // 江苏省
        {"南京市", "无锡市", "镇江市", "苏州市", "南通市", "扬州市", "盐城市", "徐州市", "淮安市", "连云港市", "常州市", "泰州市", "宿迁市"},
        // 浙江省
        {"杭州市", "宁波市", "温州市", "绍兴市", "湖州市", "嘉兴市", "金华市", "衢州市", "台州市", "丽水市", "舟山市"},
    };

    public static String[][] districtList = new String[][] {
        // 江苏省南京市 0
        {"玄武区", "白下区", "秦淮区", "建邺区", "鼓楼区", "下关区", "栖霞区", "雨花台区", "浦口区", "六合区", "江宁区", "溧水区", "高淳区"},
        // 江苏省无锡市 1
        {"崇安区", "南长区", "北塘区", "锡山区", "惠山区", "滨湖区", "无锡新区", "江阴市", "宜兴市"},
        // 江苏省镇江市 2
        {"京口区", "润州区", "丹徒区", "丹阳市", "扬中市", "句容市"},
        // 江苏省苏州市 3
        {"沧浪区", "平江区", "金阊区", "虎丘区", "吴中区", "相城区", "常熟市", "张家港市", "昆山市", "吴江区", "太仓市", "工业园区"},
        // 江苏省南通市 4
        {"港闸区", "崇川区", "南通经济技术开发区", "海安县", "如皋市", "如东县", "通州区", "海门市", "启东市"},
        // 江苏省扬州市 5
        {"广陵区", "邗江区", "维扬区", "宝应县", "仪征市", "高邮市", "江都区", "开发区"},
        // 江苏省盐城市 6
        {"亭湖区", "盐都区", "响水县", "滨海县", "阜宁县", "射阳县", "建湖县", "大丰市", "东台市"},
        // 江苏省徐州市 7
        {"鼓楼区", "云龙区", "九里区", "贾汪区", "泉山区", "丰县", "沛县", "铜山区"},
        // 江苏省淮安市 8
        {"清河区", "楚州区", "淮阴区", "清浦区"},
        // 江苏省连云港市 9
        {"新浦区", "海州区", "连云区", "赣榆县"},
        // 江苏省常州市 10
        {"天宁区", "钟楼区", "新北区", "戚墅堰区", "武进区", "金坛市", "溧阳市"},
        // 江苏省泰州市 11
        {"海陵区", "高港区", "兴化市", "靖江市", "泰兴市", "姜堰市"},
        // 江苏省宿迁市 12
        {"宿城区", "宿豫区", "沭阳县", "泗阳县", "泗洪县"},
        // 浙江省杭州市 13
        {"上城区", "下城区", "江干区", "拱墅区", "西湖区", "滨江区", "余杭区", "萧山区"},
        // 浙江省宁波市 14
        {"海曙区", "江东区", "江北区", "北仑区", "镇海区", "鄞州区"},
        // 浙江省温州市 15
        {"鹿城区", "龙湾区", "瓯海区", "洞头区", "瑞安市", "乐清市"},
        // 浙江省绍兴市 16
        {"越城区", "柯桥区", "上虞区"},
        // 浙江省湖州市 17
        {"吴兴区", "南浔区"},
        // 浙江省嘉兴市 18
        {"婺城区", "金东区", "义乌市"},
        // 浙江省衢州市 19
        {"柯城区", "衢江区", "江山市"},
        // 浙江省台州市 20
        {"椒江区", "黄岩区", "路桥区"},
        // 浙江省丽水市 21
        {"莲都区", "龙泉市"},
        // 浙江省舟山市 22
        {"定海区", "普陀区", "岱山县", "嵊泗县"},
    };

    public static String[] getProvinceList() {
        return provinceList;
    }

    public static String[] getCityList(String provinceName) {
        int cityIndex;
        switch (provinceName) {
            case "江苏省": cityIndex = 0; break;
            case "浙江省": cityIndex = 1; break;
            default: return null;
        }
        return cityList[cityIndex];
    }

    public static String[] getDistrictList(String cityName) {
        int districtIndex;
        switch (cityName) {
            case "江苏省南京市": districtIndex = 0; break;
            case "江苏省无锡市": districtIndex = 1; break;
            case "江苏省镇江市": districtIndex = 2; break;
            case "江苏省苏州市": districtIndex = 3; break;
            case "江苏省南通市": districtIndex = 4; break;
            case "江苏省扬州市": districtIndex = 5; break;
            case "江苏省盐城市": districtIndex = 6; break;
            case "江苏省徐州市": districtIndex = 7; break;
            case "江苏省淮安市": districtIndex = 8; break;
            case "江苏省连云港市": districtIndex = 9; break;
            case "江苏省常州市": districtIndex = 10; break;
            case "江苏省泰州市": districtIndex = 11; break;
            case "江苏省宿迁市": districtIndex = 12; break;
            case "浙江省杭州市": districtIndex = 13; break;
            case "浙江省宁波市": districtIndex = 14; break;
            case "浙江省温州市": districtIndex = 15; break;
            case "浙江省绍兴市": districtIndex = 16; break;
            case "浙江省湖州市": districtIndex = 17; break;
            case "浙江省嘉兴市": districtIndex = 18; break;
            case "浙江省衢州市": districtIndex = 19; break;
            case "浙江省台州市": districtIndex = 20; break;
            case "浙江省丽水市": districtIndex = 21; break;
            case "浙江省舟山市": districtIndex = 22; break;
            default: return null;
        }
        return districtList[districtIndex];
    }
}
