/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [],
  theme: {
    extend: {},
  },
  daisyui: {
        themes: [
          {
            wrath: {
                "primary": "#f87171",
                "secondary": "#2563eb",
                "accent": "#84cc16",
                "neutral": "#191D24",
                "base-100": "#2A303C",
                "info": "#3ABFF8",
                "success": "#36D399",
                "warning": "#FBBD23",
                "error": "#F87272",
            },
          },
        ],
      },
  plugins: [require("daisyui")],
}
