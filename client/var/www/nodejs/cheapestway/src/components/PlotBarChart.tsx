import React from 'react';
import ReactECharts from 'echarts-for-react';

// @ts-ignore
export function PlotBarChart(xAxis, yAxis, title, width) {
    const option = {
        title: {
            text: title,
            left: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xAxis,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: 'Direct',
                type: 'bar',
                barWidth: '60%',
                data: yAxis
            }
        ]
    };

    const myChart = <ReactECharts
        option={option}
        style={{height: '500px', width: width}}
        notMerge={true}
        lazyUpdate={true} />

    return(<div >{myChart}</div>);
}