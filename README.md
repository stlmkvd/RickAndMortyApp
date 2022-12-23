# RickAndMortyApp

RickAndMortyApp - приложение - справочник по известному шоу, в котором можно посмотреть информацию обо всех показанных в нем персонажах, локациях и эпизодах.


#Архитектура и общие принципы  

Вся архитектура приложения строится на контракте, что существуют некоторые сущности - реализации интерфейса DataItem из пакета data, вокруг которых
строится экосистема компонентов всей системы. В рамках приложения эти реализации - Personage, Episode и Location. Поскольку известно, что для каждого 
DataItem'a есть экраны со списком и деталями и схожая работа по подгрузке из model - слоя, для каждого из компонентов model и ui слоев была 
создана абстрактная реализация с использованием обощенных типов. Благодаря этому во всех классах отсутствует повторяющийся код и приложение имеет высокую 
масштабируемость и гибкость. Например, для создания конкретного фрагмента деталей нужно унаследоваться от BaseDetailFragment и определить всего один метод
 onCreateView, и котором нужно создать конкретную View, т.к. ее реализация у все DataItem'ов разная. А вся остальная логика обобщена и лежит в Base - 
фрагменте. По такой схеме строится работа всех ui - фрагментов (и не только их). Ну а в целом архитектуру можно описать как гибрид MVP и MVVM.


#Network/Database  
Приложение грузит инфу с RickAndMortyApi.com и при успешной загрузке сохраняет все в локальную базу данных. За логику работы с REST запросами отвечает 
Retrofit и RickAndMortyService - имплементация RickAndMprtyApi из пакета network.
Если данные были успешно загружены, они сохраняются в базу данных через Room. БД хранит 3 таблицы для каждого типа данных. Логика запросов к ним лежит в 
пакете database. 
Обьекты БД и RickAndMortyService являются синглтонами и имеют lazy - инициализацию.
Всю работу по работе с сетью и бд инкапсулирует синглтон - репозиторий Repository, инициализируемый единожды на старте приложения. Он не хранит в себе 
никаких данных, а является абстракцией над бэкэндом. Методы репозитория работают по схеме "загрузить из интернета" -> "если удачно, то сохранить и вернуть 
результат" -> если неудачно, то "загрузить из БД и вернуть результат" -> "если опять недачно, значит ничего не вернуть, потому что невозможно получить
данные".

#Model  
Слой модели в приложение - прослойка между ui и бэкэндом, подкласс ViewModel. Как и остальные компоненты, существует BaseViewModel и ее конкретные 
реализации - PersonageViewModel, EpisodesViewModel и LocationsViewModel. Конкретные реализации нужны, чтобы дать модели понять, как грузить в себя данные
из репозитория (или, возможно, другого источника), тк BaseViewModel строится на дженериках, а значит, из-за java - стирания типов невозможно передать в 
репозиторий информацию, какие именно DataItem'ы нужно грузить. Модель ничего не знает о реализциях своих потребителей. Она умеет кешировать данные. Кэш 
считается валидным в рамках жизни процесса приложения и очищается только когда юзер намеренно запросит refresh.

#UI  
Весь UI представлен Fragment'ами и представляет собой подклассы соответсвующих BaseFragment'ов, как было сказано в разделе "Архитектура и общие принципы".
Они все работают на дженериках, поэтому так же, как и модель, являются гибкими и расширяемыми.

#Recycler adapter  
Адаптер - изюминка всего проекта. Он инкапсулирует в себе логику обращения к модели через параметр конструктора itemProvider: BaseAdapter.ItemProvider<I>.
Это интерфейс, представляющий собой компонент, из которого BaseAdapter подгружает информацию. В рамках приложения этот интерфейс реализует только один 
класс - BaseViewModel. BaseAdapter инкапсулирует в себе логику пагинации и хранит 2 переменных MutableLiveData, через которые BaseFragment'ы могут узнать о его
состоянии, а так же дает возможность установить listener'a на нажатия на элементы Recycler'a. Он "вешается" на все RecyclerView в приложении. Как было 
сказано, адаптер отвечает за пагинацию. Когда юзер листает список до определенной отметки, адаптер подгружает новую страницу через itemProvider. 
Кстати, вся загрузка происходит асинхронно. Чтобы загрузить новые данные, адаптер вызывает itemProvider.loadPageOfItems или loadItemsByUrls. Оба этих
метода ничего не возвращают, но имеют в параметре лямбду, через которую в ui - потоке адаптер может обработать загруженные данные и обновить список. 
Асинхронность достигается благодаря корутинам и viewModelScope у модели.
Адаптер имеет 2 режима работы. Если в конструктор передать список URL соответсвующих DataItem'ов, то адаптер загрузит только итемы с этими url. Если же 
список = null, то данные будут грузиться постранично (пагинированно) начиная с 1 страницы.

#Navigation  
Вся навигация строится на работе с backstack'ами fragmentManager'ов. Есть навигация как глобальная - в рамках bootom navigation, так и внутренняя (внутри
каждой вкладки). Элементы bottomNavigation считаются равноправными и приоритетными, и если переключаться между ними, внутренние стеки очищаются. 

#Слабая связность   
Все фрагменты ничего не знают друг о друге и используют FragmentResult Api для передачи информации друг другу, за счет чего получилось достичь модульности
и взаимной изолированности элементов.

#Dagger  
Не успел запилить((((((((((((((((((((((((((((

#Unit Tests  

Мной был написан Unit-тест. Лежит в каталоге с тестами.
