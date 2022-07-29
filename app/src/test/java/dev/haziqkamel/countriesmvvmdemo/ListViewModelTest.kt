package dev.haziqkamel.countriesmvvmdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.haziqkamel.countriesmvvmdemo.model.CountriesService
import dev.haziqkamel.countriesmvvmdemo.model.Country
import dev.haziqkamel.countriesmvvmdemo.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    // 1. Create Rule
    @get:Rule
    var rule = InstantTaskExecutorRule()

    // 6. Setting Mock Service
    @Mock
    lateinit var countriesService: CountriesService

    // 7. Inject Mock into ListViewModel
    @InjectMocks
    var listViewModel = ListViewModel()

    // 8. Setup variable
    private var testSingle: Single<List<Country>>? = null

    // 9. Setup MockitoAnnotations
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    // 10. Setup the success scenario
    @Test
    fun getCountriesSuccess() {
        // 11. Setup variables
        val country = Country("countryName", "capital", "url")
        val countriesList = arrayListOf(country)

        testSingle = Single.just(countriesList)

        // 12. Mockito call api service getCountries, then return Single
        Mockito.`when`(countriesService.getCountries()).thenReturn(testSingle)

        // 13. call the Unit to test
        listViewModel.refresh()

        // 14. The size is expected to be one as set in step 11, and we return the list of country in step 12 whenever api service call
        Assert.assertEquals(1, listViewModel.countries.value?.size)
        // 15. Check the boolean variables from listViewModel
        Assert.assertEquals(false, listViewModel.countryLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    // 2. Function Before to setup the AndroidSchedulers main thread for test
    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {

            // 4. override schedulerDirect, set delay: 0
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            // 3. override createWorker
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

        // 5. Set schedulers
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

}