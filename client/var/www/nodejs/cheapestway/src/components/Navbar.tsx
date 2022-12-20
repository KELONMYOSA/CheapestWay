import {Link} from "react-router-dom";

export function Navbar() {
    return (
        <nav className="flex
        justify-between
        items-center
        h-[50px]
        px-5
        shadow-md
        bg-emerald-600
        text-slate-50">
            <Link to="/" className="font-bold">CheapestWay</Link>
            <span>
                <button>
                    <Link to="/" className="px-2 hover:text-slate-300">Search</Link>
                </button>
                <button>
                    <Link to="/statistics" className="px-2 hover:text-slate-300">Statistics</Link>
                </button>
            </span>
        </nav>
    )
}