import React, {useEffect, useState} from "react";
import {SockJS} from "../components/SockJS";
import {useSelector} from "react-redux";
import {store} from "../store/store";
import {PlotBarChart} from "../components/PlotBarChart";
import {SidenavPriceAvg} from "../components/SidenavPriceAvg";
import {PlotPriceChart} from "../components/PlotPriceChart";

export function PriceChartAvg(this: any) {
    const [sockJS, setSockJS] = useState(new SockJS());

    useSelector(state => state);

    // @ts-ignore
    let avgPrice: string = JSON.stringify(store.getState().map(home => home.data)[1])
    let avgPriceJSON
    let avgPriceX
    let avgPriceY

    if (avgPrice != null) {
        avgPrice = avgPrice.substring(1, avgPrice.length - 1).replaceAll("\\", "")
        avgPriceJSON = JSON.parse(avgPrice)
        // @ts-ignore
        avgPriceJSON.sort(function(a, b){
            return a.days - b.days;
        });
        // @ts-ignore
        avgPriceX = avgPriceJSON.map(item => item.days)
        // @ts-ignore
        avgPriceY = avgPriceJSON.map(item => item.price)
    }

    // @ts-ignore
    let avgPriceWeek: string = JSON.stringify(store.getState().map(home => home.data)[0])
    let avgPriceWeekJSON
    let avgPriceWeekX
    let avgPriceWeekY

    if (avgPriceWeek != null) {
        avgPriceWeek = avgPriceWeek.substring(1, avgPriceWeek.length - 1).replaceAll("\\", "")
        avgPriceWeekJSON = JSON.parse(avgPriceWeek)
        // @ts-ignore
        avgPriceWeekJSON.sort(function(a, b){
            return a.days - b.days;
        });
        // @ts-ignore
        let minPrice = Math.min( ...avgPriceWeekJSON.map(item => item.price) )
        // @ts-ignore
        avgPriceWeekX = avgPriceWeekJSON.map(item => item.days)
        // @ts-ignore
        avgPriceWeekY = avgPriceWeekJSON.map(item => (item.price / minPrice - 1).toFixed(3))
    }

    useEffect(() => {
        setTimeout(() => {
            sockJS.getPriceAvg()
        }, 100);
        setTimeout(() => {
            sockJS.getPriceAvgWeek()
        }, 500);
    }, []);

    const chartAvgPrice = PlotPriceChart(avgPriceX, avgPriceY)
    const chartAvgPriceWeek = PlotBarChart(avgPriceWeekX, avgPriceWeekY, null, '1000px')

    return (
        <div className="flex
        bg-gray-100
        w-full
        h-screen">
            <SidenavPriceAvg/>
            <div className="flex flex-wrap
            text-gray-500">
                <div className="mt-5">{chartAvgPrice}</div>
                <div className="mt-5">{chartAvgPriceWeek}</div>
            </div>
            <>{sockJS.sockJsClient}</>
        </div>
    )
}