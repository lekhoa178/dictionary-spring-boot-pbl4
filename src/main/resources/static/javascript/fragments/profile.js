$(function(){
    const statEl = document.getElementById("profile-stats");

    const days = JSON.parse(statEl.dataset.days.replace(/([0-9-]+)/g, '"$1"'));
    const exps = JSON.parse(statEl.dataset.exps);
    const onlineHours = JSON.parse(statEl.dataset.onlineHours);

    const max = Math.max(...exps) + 5;
    console.log(max);

    Highcharts.chart('chart', {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Thống kê quá trình học tập'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            categories: days,
            crosshair: true
        },
        yAxis: {
            min: 0,
            max: max,
            title: {
                text: 'xp/hours'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            line: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'Experiences',
            data: exps
        }, {
            name: 'Online Hours',
            data: onlineHours
        }]
    });
});
