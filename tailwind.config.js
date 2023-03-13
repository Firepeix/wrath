/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/css/app.css",
    "./src/main/kotlin/**/*.{html,js,kt}",
    "./src/main/web/*.{html,js,kt}",
  ],
  theme: {
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
  plugins: [require("daisyui")],
}
