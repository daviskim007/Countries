package com.devtides.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devtides.countries.model.CountriesService
import com.devtides.countries.model.Country
import com.devtides.countries.viewmodel.ListViewModel
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun getCountriesSuccess()   {
        val country = Country("countryName","capital","url")
        val countriesList = arrayListOf(country)

        testSingle = Single.just(countriesList)

        // When they get countries a function is called on countryService then return our test Single
        `when`(countriesService.getCountries()).thenReturn(testSingle)

        // Actually call the real functionality here that we want to test we have set up everything
        listViewModel.refresh()

        // Test the size of the countries list inside the view model is 1 and we test receiving is correct
        Assert.assertEquals(1, listViewModel.countries.value?.size)
        Assert.assertEquals(false, listViewModel.countryLoadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)
    }

    /*We need to whenever a observable is called, We need to return immediately(delay:0), And then
    we use this immediate for all these scheduling that rxJava and rxAndroid are doing.
    */
    @Before
    fun setUpRxSchedulers() {
        val immediate = object :Scheduler(){
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate}
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }

    }
}