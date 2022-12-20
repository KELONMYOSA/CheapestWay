import React from "react";
import {Routes, Route} from "react-router-dom"
import {RouteSearch} from "./pages/RouteSearch";
import {PricesStatistics} from "./pages/PricesStatistics";
import {Navbar} from "./components/Navbar";
import {Airlines} from "./pages/Airlines";
import {PriceChart} from "./pages/PriceChart";
import {PriceChartLatest} from "./pages/PriceChartLatest";
import {PriceChartAvg} from "./pages/PriceChartAvg";

function App() {
    return (
        <>
            <Navbar/>
            <Routes>
                <Route path="/" element={<RouteSearch/>}/>
                <Route path="/statistics" element={<PricesStatistics/>}/>
                <Route path="/statistics/airlines" element={<Airlines/>}/>
                <Route path="/statistics/price" element={<PriceChart/>}/>
                <Route path="/statistics/price-latest" element={<PriceChartLatest/>}/>
                <Route path="/statistics/price-avg" element={<PriceChartAvg/>}/>
            </Routes>
        </>
    )
}

export default App
