#  Firebase Chat

<table>
  <tr>
    <td align="center"><img src="/gifs/chat.gif" width="120" /></td>
    <td align="center"><img src="/gifs/profile.gif" width="120" /></td>
    <td align="center"><img src="/gifs/auth.gif" width="120" /></td>
  </tr>
</table>

##  Библиотеки

| Библиотека | Для чего | Подробнее |
| --- | --- | --- |
| Firebase | Авторизация, чат, app distribution и др. | Чат основан на firebase realtime database. Пользователи дублируются в realtime database, иначе никак. |
| Mvi Orbit | Mvi | Это скорее MVVM+. По разным причинам другие библиотеки не понравились, а своё придумывать - долго. |
| Dagger Hilt | Dependency Injection | Легко завести + compile-time ошибки. |
| Kotest | Unit-tests | Привык к этой библиотеке. Выбран [BehaviourSpec](https://kotest.io/docs/framework/testing-styles.html#behavior-spec), потому что он удобно разделяет тесты на логические блоки. |
| Mockk | Моки | Самая удобная библиотека для моков. Умеет мокать даже final классы (не использует наследование под капотом). Благодаря этому можно было избавиться от интерфейсов, но зачем?  |
| Coil | Загрузка изображений | Мне кажется, лучшая библиотека для загрузки картинок на compose. |
| Ktor | Запросы к API | Используется только для поиска картинок для профиля. Хорошая замена для Retrofit. |
| Jetpack Compose Navigation | Навигация | По другим библиотекам мало документации. Разбираться в них - долго. |

