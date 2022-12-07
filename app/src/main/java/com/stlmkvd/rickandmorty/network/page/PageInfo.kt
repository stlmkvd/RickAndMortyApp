package com.stlmkvd.rickandmorty.network.page

import java.net.URL

data class PageInfo(val count: Int, val pages: Int, val next: URL?, val prev: URL?)
