import React from 'react';
import ReactECharts from 'echarts-for-react';

// @ts-ignore
export function PlotPriceChart(xAxis, yAxis) {
    const option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        toolbox: {
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
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                type: 'line',
                data: yAxis,
                smooth: true
            }
        ]
    };

    const myChart = <ReactECharts
        option={option}
        style={{height: '500px', width: '1000px'}}
        notMerge={true}
        lazyUpdate={true} />

    return(<div >{myChart}</div>);
}