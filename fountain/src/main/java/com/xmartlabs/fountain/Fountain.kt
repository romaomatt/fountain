package com.xmartlabs.fountain

import android.arch.paging.PagedList
import android.support.annotation.VisibleForTesting
import com.xmartlabs.fountain.adapter.CachedDataSourceAdapter
import com.xmartlabs.fountain.adapter.NetworkDataSourceAdapter
import com.xmartlabs.fountain.common.IoExecutors
import com.xmartlabs.fountain.feature.cachednetwork.CachedNetworkListingCreator
import com.xmartlabs.fountain.feature.network.NetworkPagedListingCreator
import java.util.concurrent.Executor

object Fountain {
  private const val DEFAULT_FIRST_PAGE = 1
  @VisibleForTesting
  internal const val DEFAULT_NETWORK_PAGE_SIZE = 20
  private val DEFAULT_PAGED_LIST_CONFIG = PagedList.Config.Builder()
      .setPageSize(DEFAULT_NETWORK_PAGE_SIZE)
      .build()

  fun <Value> createNetworkListing(
      networkDataSourceAdapter: NetworkDataSourceAdapter<out ListResponse<Value>>,
      firstPage: Int = DEFAULT_FIRST_PAGE,
      ioServiceExecutor: Executor = IoExecutors.NETWORK_EXECUTOR,
      pagedListConfig: PagedList.Config = DEFAULT_PAGED_LIST_CONFIG
  ) = NetworkPagedListingCreator.createListing(
      firstPage = firstPage,
      ioServiceExecutor = ioServiceExecutor,
      pagedListConfig = pagedListConfig,
      networkDataSourceAdapter = networkDataSourceAdapter
  )

  fun <Value> createNetworkWithCacheSupportListing(
      networkDataSourceAdapter: NetworkDataSourceAdapter<out ListResponse<Value>>,
      cachedDataSourceAdapter: CachedDataSourceAdapter<Value>,
      ioServiceExecutor: Executor = IoExecutors.NETWORK_EXECUTOR,
      ioDatabaseExecutor: Executor = IoExecutors.DATABASE_EXECUTOR,
      firstPage: Int = DEFAULT_FIRST_PAGE,
      pagedListConfig: PagedList.Config = DEFAULT_PAGED_LIST_CONFIG
  ) = CachedNetworkListingCreator.createListing(
      cachedDataSourceAdapter = cachedDataSourceAdapter,
      firstPage = firstPage,
      ioDatabaseExecutor = ioDatabaseExecutor,
      ioServiceExecutor = ioServiceExecutor,
      pagedListConfig = pagedListConfig,
      networkDataSourceAdapter = networkDataSourceAdapter
  )
}