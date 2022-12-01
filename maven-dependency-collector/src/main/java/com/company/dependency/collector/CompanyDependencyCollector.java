package com.company.dependency.collector;

import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.dependency.graph.DependencyCollectorBuilder;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;

@Mojo(name="collector")
public class CompanyDependencyCollector extends AbstractMojo {

    @Parameter( defaultValue = "${session}", readonly = true, required = true )
    private MavenSession session;

    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    @Parameter
    private ArtifactFilter artifactFilter;

    @Parameter
    private File outputFile;

    @Parameter
    private boolean verbose;

    @Component
    private DependencyGraphBuilder graphBuilder;

    @Component
    private DependencyCollectorBuilder collectorBuilder;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest( session.getProjectBuildingRequest() );
        buildingRequest.setProject( project );
        try
        {
            DependencyNode node;
            if ( verbose )
            {
                node = collectorBuilder.collectDependencyGraph( buildingRequest, artifactFilter );
            }
            else
            {
                node = graphBuilder.buildDependencyGraph( buildingRequest, artifactFilter );
            }

            /*if ( outputFile != null )
            {
                outputFile.getParentFile().mkdirs();

                try ( Writer writer = new PrintWriter( outputFile ) )
                {
                    node.accept( new SerializingDependencyNodeVisitor( writer,
                            SerializingDependencyNodeVisitor.STANDARD_TOKENS ) );
                }

            }*/
        }
        catch ( Exception e ) // Catch all is good enough for IT
        {
            throw new MojoExecutionException( "Failed to build dependency graph", e );
        }
    }
}
