# android.io
android development framework


# Step 1. Add the JitPack repository to your build file
```
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
}

```

# Step 2. Add the dependency
```
dependencies {
        implementation 'com.github.gif-gif.android.io:androidio:0.0.10'
}
```

[![](https://jitpack.io/v/gif-gif/android.io.svg)](https://jitpack.io/#gif-gif/android.io)
