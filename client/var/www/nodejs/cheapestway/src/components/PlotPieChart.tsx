import React from 'react';
import ReactECharts from 'echarts-for-react';

// @ts-ignore
export function PlotPieChart(data, title) {
    const option = {
        title: {
            text: title,
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 40,
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: data
            }
        ]
    };

    const myChart = <ReactECharts
        option={option}
        style={{height: '500px', width: '500px'}}
        notMerge={true}
        lazyUpdate={true} />

    return(<div >{myChart}</div>);
}