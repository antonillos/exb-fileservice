package de.exb.interviews.sapozhko.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.validation.constraints.NotNull;

public interface FileService {

	@NotNull
	public OutputStream openForWriting(@NotNull final String aSessionId, @NotNull final URL aPath,
			final boolean aAppend)
		throws FileServiceException;

	@NotNull
	public InputStream openForReading(@NotNull final String aSessionId, @NotNull final URL aPath)
			throws FileServiceException;

	@NotNull
	public URL construct(@NotNull final String aSessionId, @NotNull final String aPath) throws FileServiceException;

	@NotNull
	public URL construct(@NotNull final String aSessionId, @NotNull final URL aParentPath, @NotNull final String aChildPath)
		throws FileServiceException;

	public void createNewFile(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	public void mkdir(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	public void mkdirs(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	@NotNull
	public List<URL> list(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	public void delete(@NotNull final String aSessionId, @NotNull final URL aPath, final boolean aRecursive)
			throws FileServiceException;

	public boolean exists(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	public boolean isFile(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	public boolean isDirectory(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	public long getSize(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	@NotNull
	public URL getParent(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;

	@NotNull
	public String getName(@NotNull final String aSessionId, @NotNull final URL aPath) throws FileServiceException;
}
