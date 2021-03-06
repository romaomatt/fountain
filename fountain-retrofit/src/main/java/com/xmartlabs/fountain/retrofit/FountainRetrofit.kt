package com.xmartlabs.fountain.retrofit

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import com.xmartlabs.fountain.ListResponse
import com.xmartlabs.fountain.Listing
import com.xmartlabs.fountain.adapter.CachedDataSourceAdapter
import com.xmartlabs.fountain.common.FountainConstants
import com.xmartlabs.fountain.feature.cachednetwork.CachedNetworkListingCreator
import com.xmartlabs.fountain.feature.network.NetworkPagedListingCreator
import com.xmartlabs.fountain.retrofit.adapter.NotPagedRetrifitPageFetcher
import com.xmartlabs.fountain.retrofit.adapter.RetrofitNetworkDataSourceAdapter
import com.xmartlabs.fountain.retrofit.adapter.toBaseNetworkDataSourceAdapter
import java.util.concurrent.Executor

/** A [Listing] factory */
object FountainRetrofit {
  /**
   * Creates a [Listing] with Network support.
   *
   * @param NetworkValue The listed entity type.
   * @param networkDataSourceAdapter The [RetrofitNetworkDataSourceAdapter] to manage the paged service endpoint.
   * @param firstPage The first page number, defined by the service.
   * The default value is 1.
   * @param ioServiceExecutor The [Executor] with which the service call will be made.
   * By default, it is a pool of 5 threads.
   * @param pagedListConfig The paged list configuration.
   * In this object you can specify several options, for example the [pageSize][PagedList.Config.pageSize]
   * and the [initialPageSize][PagedList.Config.initialLoadSizeHint].
   * @return A [Listing] structure with Network Support.
   */
  @Suppress("LongParameterList")
  fun <NetworkValue> createNetworkListing(
      networkDataSourceAdapter: RetrofitNetworkDataSourceAdapter<out ListResponse<out NetworkValue>>,
      firstPage: Int = FountainConstants.DEFAULT_FIRST_PAGE,
      ioServiceExecutor: Executor = FountainConstants.NETWORK_EXECUTOR,
      pagedListConfig: PagedList.Config = FountainConstants.DEFAULT_PAGED_LIST_CONFIG
  ) = NetworkPagedListingCreator.createListing(
      firstPage = firstPage,
      ioServiceExecutor = ioServiceExecutor,
      pagedListConfig = pagedListConfig,
      networkDataSourceAdapter = networkDataSourceAdapter.toBaseNetworkDataSourceAdapter()
  )

  /**
   * Creates a [Listing] with Network support from a not paged endpoint.
   *
   * @param NetworkValue The listed entity type.
   * @param notPagedRetrifitPageFetcher The [NotPagedRetrifitPageFetcher] that is used to perform the service requests.
   * @param ioServiceExecutor The [Executor] with which the service call will be made.
   * By default, it is a pool of 5 threads.
   * @return A [Listing] structure with Network Support.
   */
  fun <NetworkValue> createNotPagedNetworkListing(
      notPagedRetrifitPageFetcher: NotPagedRetrifitPageFetcher<out ListResponse<out NetworkValue>>,
      ioServiceExecutor: Executor = FountainConstants.NETWORK_EXECUTOR
  ) = NetworkPagedListingCreator.createListing(
      firstPage = FountainConstants.DEFAULT_FIRST_PAGE,
      ioServiceExecutor = ioServiceExecutor,
      pagedListConfig = FountainConstants.DEFAULT_PAGED_LIST_CONFIG,
      networkDataSourceAdapter = notPagedRetrifitPageFetcher.toBaseNetworkDataSourceAdapter()
  )

  /**
   * Creates a [Listing] with Cache + Network Support.
   *
   * @param NetworkValue The network entity type.
   * @param DataSourceValue The [DataSource] entity type.
   * @param networkDataSourceAdapter The [RetrofitNetworkDataSourceAdapter] to manage the paged service endpoint.
   * @param cachedDataSourceAdapter The [CachedDataSourceAdapter] to take control of the [DataSource].
   * @param firstPage The first page number, defined by the service.
   * The default value is 1.
   * @param ioServiceExecutor The [Executor] with which the service call will be made.
   * By default, it is a pool of 5 threads.
   * @param ioDatabaseExecutor The [Executor] through which the database transactions will be made.
   * By default the library will use a single thread executor.
   * @param pagedListConfig The paged list configuration.
   * In this object you can specify several options, for example the [pageSize][PagedList.Config.pageSize]
   * and the [initialPageSize][PagedList.Config.initialLoadSizeHint].
   * @return A [Listing] structure with Cache + Network Support.
   */
  @Suppress("LongParameterList")
  fun <NetworkValue, DataSourceValue> createNetworkWithCacheSupportListing(
      networkDataSourceAdapter: RetrofitNetworkDataSourceAdapter<out ListResponse<out NetworkValue>>,
      cachedDataSourceAdapter: CachedDataSourceAdapter<NetworkValue, DataSourceValue>,
      ioServiceExecutor: Executor = FountainConstants.NETWORK_EXECUTOR,
      ioDatabaseExecutor: Executor = FountainConstants.DATABASE_EXECUTOR,
      firstPage: Int = FountainConstants.DEFAULT_FIRST_PAGE,
      pagedListConfig: PagedList.Config = FountainConstants.DEFAULT_PAGED_LIST_CONFIG
  ) = CachedNetworkListingCreator.createListing(
      cachedDataSourceAdapter = cachedDataSourceAdapter,
      firstPage = firstPage,
      ioDatabaseExecutor = ioDatabaseExecutor,
      ioServiceExecutor = ioServiceExecutor,
      pagedListConfig = pagedListConfig,
      networkDataSourceAdapter = networkDataSourceAdapter.toBaseNetworkDataSourceAdapter()
  )

  /**
   * Creates a [Listing] with Cache + Network Support from a not paged endpoint.
   *
   * @param NetworkValue The network entity type.
   * @param DataSourceValue The [DataSource] entity type.
   * @param notPagedRetrifitPageFetcher The [NotPagedRetrifitPageFetcher] that is used to perform the service requests.
   * @param cachedDataSourceAdapter The [CachedDataSourceAdapter] to take control of the [DataSource].
   * @param ioServiceExecutor The [Executor] with which the service call will be made.
   * By default, it is a pool of 5 threads.
   * @param ioDatabaseExecutor The [Executor] through which the database transactions will be made.
   * By default the library will use a single thread executor.
   * @return A [Listing] structure with Cache + Network Support.
   */
  fun <NetworkValue, DataSourceValue> createNotPagedNetworkWithCacheSupportListing(
      notPagedRetrifitPageFetcher: NotPagedRetrifitPageFetcher<out ListResponse<out NetworkValue>>,
      cachedDataSourceAdapter: CachedDataSourceAdapter<NetworkValue, DataSourceValue>,
      ioServiceExecutor: Executor = FountainConstants.NETWORK_EXECUTOR,
      ioDatabaseExecutor: Executor = FountainConstants.DATABASE_EXECUTOR
  ) = CachedNetworkListingCreator.createListing(
      cachedDataSourceAdapter = cachedDataSourceAdapter,
      firstPage = FountainConstants.DEFAULT_FIRST_PAGE,
      ioDatabaseExecutor = ioDatabaseExecutor,
      ioServiceExecutor = ioServiceExecutor,
      pagedListConfig = FountainConstants.DEFAULT_PAGED_LIST_CONFIG,
      networkDataSourceAdapter = notPagedRetrifitPageFetcher.toBaseNetworkDataSourceAdapter()
  )
}
