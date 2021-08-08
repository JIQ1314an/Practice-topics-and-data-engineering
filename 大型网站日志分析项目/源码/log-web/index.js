$(function () {

    var baseurl = "http://localhost:8082";
    var single = {
        "ips": Math.round(Math.random()*10000000),
        "pages": Math.round(Math.random()*10000000)
    };
    
    function random(min,max,len) {
        return Array.from({length:len}, v=> Math.floor(Math.random()*(max-min))+min);
    }

    var top10ip_data = {
        "ips": ['ip1','ip2','ip3','ip4','ip5','ip6','ip7','ip8','ip9','ip10'],
        "count": random(10,1000,10)
    }

    var top10page_data = {
        "pages":  ['page1','page2','page3','page4','page5','page6','page7','page8','page9','page10'],
        "count": random(10,1000,10)
    }

    var day7add_data = {
        "times":  ['18日', '19日', '20日', '21日', '22日', '23日', '24日'],
        "counts": random(10,1000,7)
    }

    var day7login_data = {
        "times":  ['18日', '19日', '20日', '21日', '22日', '23日', '24日'],
        "counts": random(10,1000,7)
    }

    //单值指标
    $.ajax({
        type: "GET",
        url: baseurl+"/single",
        dataType: "json",
        async: false,
        success: function(data){
            console.log(1)
            if(data.code === 0) {
                console.log(data)
                single = data.data
            }else{
                swal("单值指标错误", "你的数据格式有误", "error");
            }
        },
        error: function (){
            swal("单值指标错误", "数据请求失败", "error");
        }
    });
    $("#ips").text(single.ips)
    $("#pages").text(single.pages)
    //访问前10的IP
    $.ajax({
        type: "GET",
        url: baseurl+"/ip/top10",
        dataType: "json",
        async: false,
        success: function(data){
            console.log(data)
            if(data.code === 0) {
                top10ip_data = data.data
            }else{
                swal("ip访问数前10数据错误", "你的数据格式有误", "error");
            }
        },
        error: function (){
            swal("ip访问数前10数据错误", "数据请求失败", "error");
        }
    });

    //访问前10的页面
    $.ajax({
        type: "GET",
        url: baseurl+"/page/top10",
        dataType: "json",
        async: false,
        success: function(data){
            if(data.code === 0) {
                top10page_data = data.data
            }else{
                swal("页面访问数前10数据错误", "你的数据格式有误", "error");
            }
        },
        error: function (){
            swal("页面访问数前10数据错误", "数据请求失败", "error");
        }
    });



    //连续7日新增用户数
    $.ajax({
        type: "GET",
        url: baseurl+"/user/add/count",
        dataType: "json",
        async: false,
        success: function(data){
            if(data.code === 0) {
                day7add_data = data.data
            }else{
                swal("页面访问数前10数据错误", "你的数据格式有误", "error");
            }
        },
        error: function (){
            swal("页面访问数前10数据错误", "数据请求失败", "error");
        }
    });


    //最近7日登陆用户数
    $.ajax({
        type: "GET",
        url: baseurl+"/user/login/count",
        dataType: "json",
        async: false,
        success: function(data){
            if(data.code === 0) {
                day7login_data = data.data
            }else{
                swal("页面访问数前10数据错误", "你的数据格式有误", "error");
            }
        },
        error: function (){
            swal("页面访问数前10数据错误", "数据请求失败", "error");
        }
    });


    var top10ip_option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            top: 20,
            bottom: 20,
            data: top10ip_data.ips
        },
        series: [
            {
                name: 'ip : count (proportion)',
                type: 'pie',
                radius: '55%',
                center: ['40%', '50%'],
                data: top10ip_data.count.map((value,index)=>({name:top10ip_data.ips[index],value:value})),
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    var top10page_option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            top: 20,
            bottom: 20,
            data: top10page_data.pages
        },
        series: [
            {
                name: 'url : count (proportion)',
                type: 'pie',
                radius: '55%',
                center: ['40%', '50%'],
                data: top10page_data.count.map((value,index)=>({name:top10page_data.pages[index],value:value})),
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    var day7add_option = {
        toolbox: {
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                animation: false,
                label: {
                    backgroundColor: '#505765'
                }
            }
        },
        xAxis: {
            type: 'category',
            data: day7add_data.times
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: day7add_data.counts,
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
                color: 'rgba(220, 220, 220, 0.8)'
            }
        }]
    };

    var day7login_option = {
        toolbox: {
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                animation: false,
                label: {
                    backgroundColor: '#505765'
                }
            }
        },
        xAxis: {
            type: 'category',
            data: day7login_data.times
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: day7login_data.counts,
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
                color: 'rgba(220, 220, 220, 0.8)'
            }
        }]
    };


    var top10ip = document.getElementById("top10ip");
    var top10ip_ec = echarts.init(top10ip,'macarons');
    top10ip_ec.setOption(top10ip_option);


    var top10page = document.getElementById("top10page");
    var top10page_ec = echarts.init(top10page,'macarons');
    top10page_ec.setOption(top10page_option);


    var day7add = document.getElementById("day7add");
    var day7add_ec = echarts.init(day7add,'macarons');
    day7add_ec.setOption(day7add_option);

    var day7login = document.getElementById("day7login");
    var day7login_ec = echarts.init(day7login,'macarons');
    day7login_ec.setOption(day7login_option);

});
