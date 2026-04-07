import '@/assets/main.scss'
import 'element-plus/dist/index.css'

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import router from '@/router'
import { createPinia } from 'pinia'
import locale from 'element-plus/es/locale/lang/zh-cn'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(ElementPlus, { locale })
app.mount('#app')
