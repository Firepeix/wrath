/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: "class",
  content: [
    "./src/main/resources/css/app.css",
    "./src/main/kotlin/**/*.{html,js,kt}",
    "./src/main/web/*.{html,js,kt}",
    "./node_modules/tw-elements/dist/js/**/*.js",
  ],
  theme: {
   fontFamily: {
          sans: ["Roboto", "sans-serif"],
          body: ["Roboto", "sans-serif"],
          mono: ["ui-monospace", "monospace"],
        },
    extend: {},
  },
  daisyui: {
        themes: [
          {
            wrath: {
                "primary": "#dc2626",
                "secondary": "#2563eb",
                "accent": "#22c55e",
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
  plugins: [require("tw-elements/dist/plugin")],
}
